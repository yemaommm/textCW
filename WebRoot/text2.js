Ext.onReady(function(){	
var tabs = Ext.create('Ext.tab.Panel', {
	region:'center',
	//closable:true,
	autoScroll:true,
    items: [
        {
		closable:true,
		title: 'Tab 1',
		html : 'A simple tab'
        },
        {
            title: 'Tab 2',
            html : 'Another one'
        }
    ],
    renderTo : 'tab'
});

Ext.create('Ext.button.Button', {
    text    : 'New tab',
    scope   : this,
    handler : function() {
        var tab = tabs.add({
            // we use the tabs.items property to get the length of current items/tabs
            id: "tab3",
        	title: 'Ajax Tab',
        	loader: { url: 'text3.html', params: { data: "从客户端传入的参数" }, method: 'GET',scripts:true},
        	listeners: { 
	        	activate: function(tab){ 
	        		tab.loader.load(); 
	        	} 
        	}
        });

        tabs.setActiveTab(tab);
    },
    renderTo : 'tab'
});


});