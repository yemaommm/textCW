<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
</head>
<body>
<div id="txpz" name="txpz"></div>
<script>
var xj=false;
function today()
    { 
        var now = new Date();

        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日
        
        var jm_year_month=document.getElementById('downc').innerHTML.
        	split('：')[document.getElementById('downc').innerHTML.split('：').length-1];

        if(month < 10)
            month = "0"+month;
		if(year+month>jm_year_month){
			year=jm_year_month.substr(0,4);
			month=jm_year_month.substr(4,2);
			day=new Date(year, month,0).getDate();
		}
        var clock = year + "-";
        clock += month + "-";
        if(day < 10)
            clock += "0";
        clock += day ;
        
        Query_pz_Count(year,month);
		
        return(clock); 
    } 
function Query_pz_Count(y,m){
	Ext.Ajax.request({
		url:'Query_pz_Count',
		params:{year:y,mouth:m},
		customer:'shuxing',
		success:function(response){
			document.getElementById('PZH').innerHTML=response.responseText;
		}
	});
}
Ext.onReady(function(){	
	var ZD=<%=Integer.valueOf((String) session.getAttribute("ZD"))%>;
	var CX=<%=Integer.valueOf((String) session.getAttribute("CX"))%>;
	var ZZ=<%=Integer.valueOf((String) session.getAttribute("ZZ"))%>;
	if(ZD==1)
		ZD=true;
	if(CX==1)
		CX=true;
	if(ZZ==1)
		ZZ=true;
	var b=false;
	var updata=false;
	
	Ext.create('Ext.data.Store', {
	    storeId:'simpsonsStore',
	    fields:['ZY', 'KM','XJLLH', 'JF','DF'],
	    //data:{'items':[
	    //    { "ZY": "",  "KM":"",  "JF":"0"  ,"DF":"0"},
		//	{ "ZY": "",  "KM":"",  "JF":"0"  ,"DF":"0"}
	    //]},
	    proxy: {
	    	type: 'ajax',  
            url: 'PZCX',// 向该URL发送Ajax请求  
            reader: { // 使用Ext.data.reader.Json读取服务器数据  
                type: 'json',  
                root: 'data' // 直接读取服务器响应的data数据  
            }
	    }
	});
	var grid=Ext.create('Ext.grid.Panel',{
		title:'<div align="center">记账凭证</div>',
		//renderTo:'txpz',
		forceFit:true,
		//sortableColumns:false,
		frame:true,
		store: Ext.data.StoreManager.lookup('simpsonsStore'),
		columns:[
			{ header: '摘要',width:170,dataIndex: 'ZY' ,sortable: false  ,editor: 
				{xtype: 'textfield',allowBlank: false,hideTrigger:true,
					listeners : {
						'Focus': function(){  
								rowIndex = grid.store.indexOf(grid.getSelectionModel().getSelection()[0])+1;
								if(rowIndex!=1){
									var i=0;
									for(i=0;i<rowIndex-1;i++){
										if(grid.store.getAt(i).get('ZY').toString()==''){
											grid.getSelectionModel().select(i);
											ce.startEdit(i,0);
											return;
										}
										if(grid.store.getAt(i).get('KM').toString()==''){
											grid.getSelectionModel().select(i);
											ce.startEdit(i,0);
											ce.startEdit(i,1);
											return;
										}
										if(grid.store.getAt(i).get('JF')==grid.store.getAt(i).get('DF')){
											grid.getSelectionModel().select(i);
											ce.startEdit(i,0);
											ce.startEdit(i,2);
											return;
										}
										
									}
									//alert(i)
								}
								if(rowIndex==grid.store.getCount())
									grid.store.add([['','','','0','0']]);
                            }
					}
				}
			},
			{ header: '科目名称', dataIndex: 'KM', flex: 1 ,sortable: false  ,
				editor: {
					xtype:'combo',
					//hideTrigger :true,
		     		store: Ext.create('Ext.data.Store', {
		     		    fields: [{name:'BMb'},{name:'KMMCb'}],
		     		   proxy:{
							type:'ajax',
							url:'Json_km',
							reader:new Ext.data.ArrayReader({
								fields: [{name:'BMb'},{name:'KMMCb'}]
							})
						},
						autoLoad:true
		     		}),
		     		forceSelection:true,
		     		autoSelect:true,
		     	    queryMode: 'local',
					//hiddenName:'BM',
		     	    displayField: 'KMMCb',
					allowBlank: false,
					listeners : {
						'Focus': function(f){  //alert(grid.store.getAt(0).get('ZY'))
								rowIndex = grid.store.indexOf(grid.getSelectionModel().getSelection()[0])+1;
								XLROW = grid.store.indexOf(grid.getSelectionModel().getSelection()[0])+1;
								if(rowIndex!=1){
									var i=0;
									for(i=0;i<rowIndex-1;i++){
										if(grid.store.getAt(i).get('ZY').toString()==''){
											grid.getSelectionModel().select(i);
											ce.startEdit(i,1);
											ce.startEdit(i,0);
											return;
										}
										if(grid.store.getAt(i).get('KM').toString()==''){
											grid.getSelectionModel().select(i);
											ce.startEdit(i,1);
											ce.startEdit(i,1);
											return;
										}
										if(grid.store.getAt(i).get('JF')==grid.store.getAt(i).get('DF')){
											grid.getSelectionModel().select(i);
											ce.startEdit(i,1);
											ce.startEdit(i,2);
											return;
										}
									}
									//alert(i)
								}
								if(rowIndex==grid.store.getCount())
									grid.store.add([['','','','0','0']]);
                            },
    						'blur': function(f,t,g){
    							//rowIndex = grid.store.indexOf(grid.getSelectionModel().getSelection()[0])+1;
    							if(f.getValue()!=''){
    								if(f.getValue().substring(0,4)=='1001'||f.getValue().substring(0,4)=='1002')
    									xjllwin.show();
    								else
    									grid.store.getAt(rowIndex-1).set('XJLLH',0);
    							}
    								
    						}
							
					}
				}
			},
			{ header: '流量',width:50,dataIndex: 'XJLLH' ,sortable: false  
		},
			{align : 'right', header: '借', dataIndex: 'JF', xtype: 'numbercolumn', flex: 1 ,sortable: false, format:'0,000.00',
				editor: {xtype: 'numberfield',allowBlank: false,hideTrigger:true,
					listeners : {
						'Focus': function(){  //alert(grid.store.getAt(0).get('ZY'))
								rowIndex = grid.store.indexOf(grid.getSelectionModel().getSelection()[0])+1;
								if(rowIndex!=1){
									var i=0;
									for(i=0;i<rowIndex-1;i++){
										if(grid.store.getAt(i).get('ZY').toString()==''){
											grid.getSelectionModel().select(i);
											ce.startEdit(i,2);
											ce.startEdit(i,0);
											return;
										}
										if(grid.store.getAt(i).get('KM').toString()==''){
											grid.getSelectionModel().select(i);
											ce.startEdit(i,2);
											ce.startEdit(i,1);
											return;
										}
										if(grid.store.getAt(i).get('JF')==grid.store.getAt(i).get('DF')){
											grid.getSelectionModel().select(i);
											ce.startEdit(i,2);
											ce.startEdit(i,2);
											return;
										}
									}
									//alert(i)
								}
								if(rowIndex==grid.store.getCount())
									grid.store.add([['','','','0','0']]);
                            },
						'blur': function(f,t,g){  //alert(g[0].get('DF'))
								if(f.getValue()!='0'){	
									if(grid.store.getAt(rowIndex-1).get('DF')!=0)
										grid.store.getAt(rowIndex-1).set('DF',0)

									if(rowIndex==grid.store.getCount()-1){
										if(b)
											b=false;
										else{
											grid.store.add([['','','','0','0']]);
											b=true;
										}
									}
									
									grid.getSelectionModel().select(rowIndex);

									var sum=0;
									for(var i=0;i<grid.store.getCount( );i++){
										sum+=parseFloat(grid.store.getAt(i).get('DF'));
									}
									document.getElementById('D').innerHTML=Ext.util.Format.number(sum,'0,000.00');
									
									sum=0;
									for(var i=0;i<grid.store.getCount( );i++){
										if(i==rowIndex-1){
											sum+=parseFloat(f.getValue());
										}else{
											sum+=parseFloat(grid.store.getAt(i).get('JF'));
										}
									}
									document.getElementById('J').innerHTML=Ext.util.Format.number(sum,'0,000.00');
								}
                            }
					}
				}
			},
			{align : 'right', header: '贷', dataIndex: 'DF', xtype: 'numbercolumn', flex: 1 ,sortable: false, format:'0,000.00',
				editor: {xtype: 'numberfield',allowBlank: false,hideTrigger:true,
					listeners : {
						'Focus': function(){  
								rowIndex = grid.store.indexOf(grid.getSelectionModel().getSelection()[0])+1;
								if(rowIndex!=1){
									var i=0;
									for(i=0;i<rowIndex-1;i++){
										if(grid.store.getAt(i).get('ZY').toString()==''){
											grid.getSelectionModel().select(i);
											ce.startEdit(i,3);
											ce.startEdit(i,0);
											return;
										}
										if(grid.store.getAt(i).get('KM').toString()==''){
											grid.getSelectionModel().select(i);
											ce.startEdit(i,3);
											ce.startEdit(i,1);
											return;
										}
										if(grid.store.getAt(i).get('JF')==grid.store.getAt(i).get('DF')){
											grid.getSelectionModel().select(i);
											ce.startEdit(i,3);
											ce.startEdit(i,2);
											return;
										}
									}
									//alert(i)
								}
								if(rowIndex==grid.store.getCount())
									grid.store.add([['','','','0','0']]);
                            },
						'blur': function(f,t,g){  //alert(grid.getSelectionModel().getSelection()[0].get('ZY').toString())
								if(f.getValue()!='0'){
									if(grid.store.getAt(rowIndex-1).get('JF')!=0)
										grid.store.getAt(rowIndex-1).set('JF',0)

									if(rowIndex==grid.store.getCount()-1){
										if(b)
											b=false;
										else{
											grid.store.add([['','','','0','0']]);
											b=true;
										}
									}
									
									grid.getSelectionModel().select(rowIndex);
									
									var sum=0;
									for(var i=0;i<grid.store.getCount( );i++){
										if(i==rowIndex-1){
											sum+=parseFloat(f.getValue());
										}else{
											sum+=parseFloat(grid.store.getAt(i).get('DF'));
										}
									}
									document.getElementById('D').innerHTML=Ext.util.Format.number(sum,'0,000.00');
									
									sum=0;
									for(var i=0;i<grid.store.getCount( );i++){
										sum+=parseFloat(grid.store.getAt(i).get('JF'));
									}
									document.getElementById('J').innerHTML=Ext.util.Format.number(sum,'0,000.00');
								} 
                            }
					}
				}
			}
		],
		plugins: [
			ce=Ext.create('Ext.grid.plugin.CellEditing', {
				clicksToEdit: 1
			})
		],
		dockedItems: [{
            dock: 'top',
            xtype: 'toolbar',
            layout: 'column',
            items: [{
				xtype:'label',
				columnWidth: .50,
                html: '&nbsp;&nbsp;&nbsp;'
			},{
				xtype:'label',
				//columnWidth: .40,
                html: '|&nbsp;&nbsp;&nbsp;&rarr;'
			},{
				id:'datePZ',
				xtype:'datefield',
				format:'Y-m-d',
				value:today(),
				editable : false,
				listeners : {
					'change': function(f){  
						var datePZ=f.getRawValue();
						//alert(datePZ.split('-')[0]+datePZ.split('-')[1]);
						Query_pz_Count(datePZ.split('-')[0],datePZ.split('-')[1]);
					}
				}
			},{
				xtype:'label',
                html: '&larr;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&rarr;'
			},{
				icon:'ext/resources/ext-theme-access/images/grid/page-prev-disabled.gif',
                handler: function(){ 
                	var pzh=document.getElementById('PZH').innerHTML;
                	pzh=parseInt(pzh)-1;
                	var dd=Ext.getCmp('datePZ').getRawValue();
                	grid.store.load({
 					    params: {
 					    	year: dd.split("-")[0],
 					    	mouth: dd.split("-")[1],
 					    	date: dd.split("-")[2],
 					    	PZH: pzh
 					    },
 					    callback: function(records, operation, success) {
 					        // do something after the load finishes
 					    	Ext.Ajax.request({
 		                		url:'PZCX',
 		                		params:{year:'0'},
 		                		success:function(response){
 		                			var msg=response.responseText;
 		                			var spzh=msg.split(':')[0];
 	 		                		var spsh=msg.split(':')[5];
 	 		                		var spzf=msg.split(':')[6];
 	 		                		document.getElementById('PZH').innerHTML=spzh;
 	 		                		if(spsh=="1"){
 	 		                			document.getElementById('SH').innerHTML='已审核';
 	 		                		}else{
 	 		                			document.getElementById('SH').innerHTML='';
 	 		                		}
 	 								if(spzf=="1"){
 	 									document.getElementById('ZF').innerHTML='已作废';
 	 		                		}else{
 	 		                			document.getElementById('ZF').innerHTML='';
 	 		                		}
 		                		},
 		                		error:function(response){
 		                			var msg=response.responseText;
 		                		}
 		            		});
 					    },
 					    scope: this
 					});
                	//alert(grid.store.getCount());
                	
                }
			},{
				xtype:'label',
				Width: 50,
                html: '<div id="PZH">凭证号</div>'
			},{
				icon:'ext/resources/ext-theme-access/images/grid/page-next-disabled.gif',
                handler: function(){ 
                	var pzh=document.getElementById('PZH').innerHTML;
                	pzh=parseInt(pzh)+1;
                	var dd=Ext.getCmp('datePZ').getRawValue();
                	grid.store.load({
 					    params: {
 					    	year: dd.split("-")[0],
 					    	mouth: dd.split("-")[1],
 					    	date: dd.split("-")[2],
 					    	PZH: pzh
 					    },
 					    callback: function(records, operation, success) {
 					        // do something after the load finishes
 					    	Ext.Ajax.request({
 		                		url:'PZCX',
 		                		params:{year:'0'},
 		                		success:function(response){
 		                			var msg=response.responseText;
 		                			var spzh=msg.split(':')[0];
 	 		                		var spsh=msg.split(':')[5];
 	 		                		var spzf=msg.split(':')[6];
 	 		                		document.getElementById('PZH').innerHTML=spzh;
 	 		                		if(spsh=="1"){
 	 		                			document.getElementById('SH').innerHTML='已审核';
 	 		                		}else{
 	 		                			document.getElementById('SH').innerHTML='';
 	 		                		}
 	 								if(spzf=="1"){
 	 									document.getElementById('ZF').innerHTML='已作废';
 	 		                		}else{
 	 		                			document.getElementById('ZF').innerHTML='';
 	 		                		}
 		                		},
 		                		error:function(response){
 		                			var msg=response.responseText;
 		                		}
 		            		});
 					    },
 					    scope: this
 					});
                }
			},{
				xtype:'label',
                html: '&larr;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<b><font size="3" color="red"><span id="SH"></span><span id="ZF"></span></font></b>'
			}]
        },{
            dock: 'bottom',
			xtype: 'toolbar',
			layout: 'column',
			height:30,
            items: [{
				name:'heji',
                xtype:'label',
                columnWidth: .50,
                html: '<b>合计金额:</b>'
            },{
                xtype:'label',
                columnWidth: .25,
                html: '<b>借方&nbsp;:&nbsp;<span id="J">0.00</span></b>'
            },{
                xtype:'label',
                columnWidth: .25,
                html: '<b>贷方&nbsp;:&nbsp;<span id="D">0.00</span></b>'
            }]
		}]
	});
	
	var pzzz_Panel = new Ext.Panel({
		//title:'记账凭证',
		renderTo:'txpz',
		layout:'fit',
		//frame:true,
		items:[grid],
		bbar:[{
                text: '保存',
                disabled : !ZD,
				icon:'ext/resources/ext-theme-neptune/images/grid/group-expand.png',
                handler: function(){
                	if(document.getElementById('SH').innerHTML!=''){
                		Ext.Msg.alert('提示','已审核的凭证不能保存');
                		return;
                	}
                	if(document.getElementById('ZF').innerHTML!=''){
                		Ext.Msg.alert('提示','已作废的凭证不能保存');
                		return;
                	}
                	var rowsData = [];
                	var count = grid.store.getCount(); 
                	var record; 
                	var Trecord;
                	for (var i = 0; i < count; i++) { 
                		Trecord = grid.store.getAt(i); 
	                	var y1=Trecord.get('ZY')==''&&Trecord.get('KM')==''&&Trecord.get('JF')==0&&Trecord.get('DF')==0;
	                	var y2=Trecord.get('ZY')!=''&&Trecord.get('KM')!='';
	                	var y3=Trecord.get('JF')!=0||Trecord.get('DF')!=0;
	                	
	                	if(i==0){
	                		if(y1){
	                			Ext.Msg.alert('提示','请填写摘要');
	                			return;
		                	}
	                	}
	                	if(!y1&&(!y2||!y3)){
	                		if(record.get('ZY')==''){
	                			Ext.Msg.alert('提示','请填写摘要');
	                			return;
	                		}
	                		if(record.get('KM')==''){
	                			Ext.Msg.alert('提示','请填写科目');
	                			return;
	                		}
	                		if(record.get('JF')==0&&record.get('DF')==0){
	                			Ext.Msg.alert('提示','请填写金额');
	                			return;
	                		}
	                		
	                	}
	                	if(!y1){
	                		record = grid.store.getAt(i); 
	                		rowsData.push(record.data); 
	                	}
	                	
	                	//if (record.dirty) { 
	                		
	                	//} 
                	} 
                	
                	var jsonrowData=Ext.encode(rowsData);
                	
                	var J=document.getElementById('J').innerHTML;
                	var D=document.getElementById('D').innerHTML;

					
                	if(J!=D){
                		Ext.Msg.alert('提示','借方与贷方金额不相等');
            			return;
                	}
                	
                	var PZH=document.getElementById('PZH').innerHTML;
                	var datePZ=Ext.getCmp('datePZ').getRawValue();
                	
                	if(updata){
                		Ext.Ajax.request({
	                		url:'Updatapz',
	                		params:{data:jsonrowData,datePZ:datePZ,PZH:PZH},
	                		customer:'shuxing',
	                		success:function(response){
	                			var msg=response.responseText;
	                			Ext.Msg.alert('提示',msg);
	                			updata=true;
	                		},
	                		error:function(response){
	                			var msg=response.responseText;
	                			Ext.Msg.alert('提示',msg);
	                		}
	            		});
                	}else{
	                	Ext.Ajax.request({
	                		url:'Addpz',
	                		params:{data:jsonrowData,datePZ:datePZ,PZH:PZH},
	                		customer:'shuxing',
	                		success:function(response){
	                			var msg=response.responseText;
	                			Ext.Msg.alert('提示',msg);
	                			updata=true;
	                		},
	                		error:function(response){
	                			var msg=response.responseText;
	                			Ext.Msg.alert('提示',msg);
	                		}
	            		});
                	}
                	
                }
            },{
                text: '添加凭证',
                disabled : !ZD,
				icon:'ext/resources/ext-theme-neptune/images/grid/group-expand.png',
                handler: function(){
                	var datePZ=Ext.getCmp('datePZ').getRawValue();
                	if(document.getElementById('SH').innerHTML!=''||document.getElementById('ZF').innerHTML!=''){
                		Query_pz_Count(datePZ.split('-')[0],datePZ.split('-')[1]);
                    	grid.store.load();
            			updata=false;
            			document.getElementById('SH').innerHTML=''
						document.getElementById('ZF').innerHTML=''
                		return;
                	}
                	var rowsData = [];
                	var count = grid.store.getCount(); 
                	var record; 
                	var Trecord; 
                	for (var i = 0; i < count; i++) { 
                		Trecord = grid.store.getAt(i); 
                		var y1=Trecord.get('ZY')==''&&Trecord.get('KM')==''&&Trecord.get('JF')==0&&Trecord.get('DF')==0;
	                	var y2=Trecord.get('ZY')!=''&&Trecord.get('KM')!='';
	                	var y3=Trecord.get('JF')!=0||Trecord.get('DF')!=0;
	                	
	                	if(i==0){
	                		if(y1){
	                			Ext.Msg.alert('提示','请填写摘要');
	                			return;
		                	}
	                	}
	                	if(!y1&&(!y2||!y3)){
	                		if(record.get('ZY')==''){
	                			Ext.Msg.alert('提示','请填写摘要');
	                			return;
	                		}
	                		if(record.get('KM')==''){
	                			Ext.Msg.alert('提示','请填写科目');
	                			return;
	                		}
	                		if(record.get('JF')==0&&record.get('DF')==0){
	                			Ext.Msg.alert('提示','请填写金额');
	                			return;
	                		}
	                		
	                	}
	                	if(!y1){
	                		record = grid.store.getAt(i); 
	                		rowsData.push(record.data); 
	                	}
	                	//if (record.dirty) { 
	                		
	                	//} 
                	} 
                	
                	var jsonrowData=Ext.encode(rowsData);
                	var J=document.getElementById('J').innerHTML;
                	var D=document.getElementById('D').innerHTML;

					
                	if(J!=D){
                		Ext.Msg.alert('提示','借方与贷方金额不相等');
            			return;
                	}
                	
                	
                	var PZH=document.getElementById('PZH').innerHTML;
                	
               		Ext.Ajax.request({
                		url:'Updatapz',
                		params:{data:jsonrowData,datePZ:datePZ,PZH:PZH},
                		customer:'shuxing',
                		success:function(response){
                			var msg=response.responseText;
                			Ext.Msg.alert('提示',msg);
                			Query_pz_Count(datePZ.split('-')[0],datePZ.split('-')[1]);
                        	grid.store.load();
                			updata=false;
                		},
                		error:function(response){
                			var msg=response.responseText;
                			Ext.Msg.alert('提示',msg);
                		}
            		});
                	
                }
            },{
                text: '删除行',
                disabled : !ZD,
				icon:'ext/resources/ext-theme-neptune/images/grid/group-collapse.png',
                handler: function(){ 
                	if(document.getElementById('SH').innerHTML!=''){
                		Ext.Msg.alert('提示','已审核的凭证不能修改');
                		return;
                	}
                	if(document.getElementById('ZF').innerHTML!=''){
                		Ext.Msg.alert('提示','已作废的凭证不能修改');
                		return;
                	}
                	
                	grid.store.removeAt(grid.store.indexOf(grid.getSelectionModel().getSelection()[0]));
					var sum=0;
					for(var i=0;i<grid.store.getCount( );i++){
						sum+=parseFloat(grid.store.getAt(i).get('DF'));
					}
					document.getElementById('D').innerHTML=Ext.util.Format.number(sum,'0,000.00');
					
					sum=0;
					for(var i=0;i<grid.store.getCount( );i++){
						sum+=parseFloat(grid.store.getAt(i).get('JF'));
					}
					document.getElementById('J').innerHTML=Ext.util.Format.number(sum,'0,000.00');
                }
			},{
                text: '审核',
                disabled : !ZZ,
				icon:'ext/resources/ext-theme-neptune/images/grid/drop-yes.png',
                handler: function(){
                	if(document.getElementById('SH').innerHTML!=''){
                		Ext.Msg.alert('提示','此凭证已审核');
                		return;
                	}
                	if(document.getElementById('ZF').innerHTML!=''){
                		Ext.Msg.alert('提示','已作废的凭证不能审核');
                		return;
                	}
                	var datePZ=Ext.getCmp('datePZ').getRawValue();
                	var PZH=document.getElementById('PZH').innerHTML;
                	
                	Ext.Ajax.request({
                		url:'SH_PZ',
                		params:{date:datePZ,PZH:PZH,SH:'true'},
                		customer:'shuxing',
                		success:function(response){
                			var msg=response.responseText;
                			document.getElementById('SH').innerHTML='已审核';
                			Ext.Msg.alert('提示',msg);
                		},
                		error:function(response){
                			var msg=response.responseText;
                			Ext.Msg.alert('提示',msg);
                		}
            		});
                }
			},{
                text: '取消审核',
                disabled : !ZZ,
				icon:'ext/resources/ext-theme-neptune/images/grid/drop-yes.png',
                handler: function(){
                	if(document.getElementById('SH').innerHTML==''){
                		Ext.Msg.alert('提示','此凭证未审核');
                		return;
                	}
                	if(document.getElementById('ZF').innerHTML!=''){
                		Ext.Msg.alert('提示','已作废的凭证不能审核');
                		return;
                	}
                	var datePZ=Ext.getCmp('datePZ').getRawValue();
                	var PZH=document.getElementById('PZH').innerHTML;
                	
                	Ext.Ajax.request({
                		url:'SH_PZ',
                		params:{date:datePZ,PZH:PZH,SH:'false'},
                		customer:'shuxing',
                		success:function(response){
                			var msg=response.responseText;
                			document.getElementById('SH').innerHTML='';
                			Ext.Msg.alert('提示',msg);
                		},
                		error:function(response){
                			var msg=response.responseText;
                			Ext.Msg.alert('提示',msg);
                		}
            		});
                }
			},{
                text: '删除凭证',
                disabled : !ZD,
				icon:'ext/resources/ext-theme-neptune/images/grid/drop-yes.png',
                handler: function(){
                	if(document.getElementById('SH').innerHTML!=''){
                		Ext.Msg.alert('提示','已审核的凭证不能删除');
                		return;
                	}
                	Ext.MessageBox.confirm('提示','请确认是否删除',function(id){
                		if(id=='yes'){
                			var datePZ=Ext.getCmp('datePZ').getRawValue();
                        	var PZH=document.getElementById('PZH').innerHTML;
                        	
                        	Ext.Ajax.request({
                        		url:'ZF_PZ',
                        		params:{date:datePZ,PZH:PZH},
                        		customer:'shuxing',
                        		success:function(response){
                        			var msg=response.responseText;
                        			Ext.Msg.alert('提示',msg);
                        		},
                        		error:function(response){
                        			var msg=response.responseText;
                        			Ext.Msg.alert('提示',msg);
                        		}
                        	});
                		}
                	});
                	
                }
			}]
	 });
	grid.store.load();
	
	var XJLL=new Ext.form.Panel({
    	frame:true,
    	defaults:{
    		labelSeparator:'：',
    		allowBlank:false,
    		blankText:'不允许为空',
    		labelAlign:'left',
    		msgTarget:'side'
    	},
    	items:[{
    		xtype:'combo',
			//hideTrigger :true,
     		store: Ext.create('Ext.data.Store', {
     		    fields: [{name:'BMb'},{name:'KMMCb'}],
     		   proxy:{
					type:'ajax',
					url:'JSON_XJLL',
					reader:new Ext.data.ArrayReader({
						fields: [{name:'BMb'},{name:'KMMCb'}]
					})
				},
				autoLoad:true
     		}),
     		forceSelection:true,
     		autoSelect:true,
     	    queryMode: 'local',
			//hiddenName:'BM',
     	    displayField: 'KMMCb',
     	   	valueField:'BMb',
			allowBlank: false,
			name:'xjllcombo'
    	}],
    	buttons:[{
				text:'确定',
				handler:function(){
					var stmpxj=XJLL.getForm().findField('xjllcombo').getValue();
					//rowIndex = grid.store.indexOf(grid.getSelectionModel().getSelection()[0])+1;
					grid.store.getAt(rowIndex-1).set('XJLLH',stmpxj);
                	xj=false;
                	xjllwin.close();
			}
		}]
    });
	xjllwin=new Ext.Window({  
        title:'现金流量',  
        width:300,  
        height:105,  
        closeAction:'hide',  
        plain:true,  
        layout:'fit',
        constrain:true,
        resizable:true,
        modal:true,
        closable: false,
        //maximizable:true,
        items:[XJLL]
	});
});
</script>
</body>
</html>
