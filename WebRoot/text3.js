
	Ext.onReady(function(){
		var tree = Ext.create("Ext.tree.Panel", {
			                 title: "Tree示例",
			                 width: 150,
			                 height: 100,
			                 renderTo:'div',
			                 root: {
		                     text: "根",
			                     expanded: true,
			                     children: [{
			                         text: "叶子1",
			                         leaf: true
			                     }, {
			                         text: "叶子2",
			                         leaf: true
			                     }]
			                 }
			             });
			        
	
	});
