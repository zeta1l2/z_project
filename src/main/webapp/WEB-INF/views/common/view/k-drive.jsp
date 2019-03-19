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
	var treeview = $("#treeview-left").kendoTreeView({
	    dragAndDrop: true,
	    select: onSelect,
	    dragstart: onDragStart,
		drag: onDrag,
		drop: onDrop,
		dragend: onDragEnd,
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
		var selectedNode = treeview.select();
		
		treeview.append({
			text: $("#appendNodeText").val()			
		}, selectedNode);
	});

	function onSelect(e) {		
	    console.log("Selecting: " + this.text(e.node));	    
	}
	
	function onDragStart(e) {
		console.log("Started dragging " + this.text(e.sourceNode));		
	}
	
	function onDrag(e) {
		console.log("Dragging " + this.text(e.sourceNode));
	}
	
	function onDrop(e) {
		console.log(
	    "Dropped " + this.text(e.sourceNode) +
	    " (" + (e.valid ? "valid" : "invalid") + ")"
	    );
	}
	
	function onDragEnd(e) {
		console.log("Finished dragging end");		
	}	

	$("#treeview-right").kendoTreeView({
	    dragAndDrop: true,
	    select: onSelect,
	    dragstart: onDragStart,
		drag: onDrag,
		drop: onDrop,
		dragend: onDragEnd,
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