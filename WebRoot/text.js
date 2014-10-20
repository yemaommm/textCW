Ext.onReady(function(){
	var mypanel=new Ext.Panel({
		id:'mypanel',
		renderTo:Ext.getBody(),
		title:'mypanel',
		collapsible:true,
		floating:true,
		autoScroll:true,
		//loader:{ url: "index.html",autoLoad:true,script:true},//º”‘ÿ1.htm“≥√Ê
		buttons:[{
			text:'jiazai',
			handler:function(){
				Ext.getCmp('mypanel').loader({
					url:'text.html'
				});
			}
		}],width:400,
		height:300
	});
});