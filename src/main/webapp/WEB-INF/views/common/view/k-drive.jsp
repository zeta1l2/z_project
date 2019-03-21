<div class="inner-block">
	<div class='row'>		
		<div class="col-sm-3" style="text-align:left;">
			<h4>Treeview One</h4>
			<div id="treeview-left"></div>
		    <h4 style="padding-top: 2em;">Treeview Two</h4>
		    <div id="treeview-right"></div>
		</div>
		<div class="col-sm-9">			
			<table class="table">			
				<tr>
					<th>Name</th>														
					<th>Date</th>
				</tr>
				<tr>
					<td style="text-align:left">File1</td>						
					<td style="text-align:left">2019.3.15</td>
				</tr>
			</table>

			<ul class="options">
				<li>
					<input id="appendNodeNo" value="No" class="k-textbox"/>
					<input id="appendNodeText" value="Node" class="k-textbox"/>
					<button class="k-button" id="appendNodeToSelected">Append node</button>
				</li>				
			</ul>
		</div>		
	</div>
</div>

<script>
$(document).ready(function() {
	var numId = 8;
	var treeview = $("#treeview-left").kendoTreeView({
	    dragAndDrop: true,
	    select: onSelect,
	    drop: onDrop,
	    drag: onDrag,
	    dataSource: [
	        { text: "Furniture", expanded: true, id: 1, items: [
	            { text: "Tables & Chairs", id: 2 },
	            { text: "Sofas", id: 3 },
	            { text: "Occasional Furniture", id: 4 }
	        ] },
	        { text: "Decor", id: 5, items: [
	            { text: "Bed Linen", id: 6 },
	            { text: "Curtains & Blinds", id: 7 },
	            { text: "Carpets", id: 8 }
	        ] }
	    ],
	    loadOnDemand: false
    }).data("kendoTreeView");
	
	$("#appendNodeToSelected").click(function(e) {		
		var barDataItem = treeview.dataSource.get($("#appendNodeNo").val());
		var barElement = treeview.findByUid(barDataItem.uid);		
		treeview.select(barElement);
				
		treeview.append({
			text: $("#appendNodeText").val(), id: ++numId 			
		}, barElement);		
	});

	function onSelect(e) {	    
	    var bar = treeview.dataItem(e.node);
	    console.log(bar.id)
	}
	
	function onDragStart(e) {
		console.log("Started dragging " + this.text(e.sourceNode));				
	}
	
	function onDrag(e) {
		if (e.statusClass.indexOf("insert") >= 0) {
		    // deny the operation
		    e.setStatusClass("k-i-cancel");
		    treeview.expand(e.dropTarget);
		}
	}
	
	function onDrop(e) {
		if(e.valid) {
			if (kendo.keys.CTRL == e.keyCode) {
                console.log("ctrl key press");
            }
			var start = treeview.dataItem(e.sourceNode);
			var end = treeview.dataItem(e.dropTarget);
			console.log("Select : " + start.id + "  Target : " + end.id);
		}
	}
	
	function onDragEnd(e) {
		console.log("Finished dragging end");		
	}	

	$("#treeview-right").kendoTreeView({	    	    
	    dataSource: [
	        { text: "Storage", expanded: true, uid: 9, items: [
	            { text: "Wall Shelving", uid: 10 },
	            { text: "Floor Shelving", uid: 11 },
	            { text: "Kids Storage", uid: 12 }]},
	        { text: "Lights", uid: 13, items: [
	            { text: "Ceiling", uid: 14 },
	            { text: "Table", uid: 15 },
	            { text: "Floor", uid: 16 }
	        ]}
	    ]
	});
});
</script>