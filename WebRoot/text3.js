
	Ext.onReady(function(){
		var tree = Ext.create("Ext.tree.Panel", {
			                 title: "Treeʾ��",
			                 width: 150,
			                 height: 100,
			                 renderTo:'div',
			                 root: {
		                     text: "��",
			                     expanded: true,
			                     children: [{
			                         text: "Ҷ��1",
			                         leaf: true
			                     }, {
			                         text: "Ҷ��2",
			                         leaf: true
			                     }]
			                 }
			             });
			        
	
	});
