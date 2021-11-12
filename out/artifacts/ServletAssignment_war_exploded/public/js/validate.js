$(document).ready(function(){
	$('#fishName').focusout(function(){
		checkFish();
	});
	$('#pondNameSelect').focusout(function(){
		checkFish();
	});
	
	$('#pondName').focusout(function(){
		checkPond();
	});
	
	
	
	function checkFish(){
    	var fishName = $('#fishName').val();
    	var pondId = $('#pondNameSelect').val();
    	var actionValue = document.getElementById("myForm").action;
    	var indexOfId = actionValue.indexOf("id=");
    	var fishID = 0;
    	if(indexOfId>=0){
    		fishID = actionValue.substring(indexOfId+3);
    	} 
    	var data = {
    			id: fishID,
    			fishName : fishName,
    			pondId : pondId,
    	};
    	$.ajax({
    		type : "POST",
    		url: "FishCheck",
    		data: data,
    		success : function(responseText){
    			if(responseText != ""){
    				$("#fishNameMessage").removeClass("hiddenField");
    				$("#fishNameMessage").text(responseText);
    				$("button").attr("disabled", true);
    			} else {
    				$("#fishNameMessage").addClass("hiddenField");
    				$("button").removeAttr("disabled");
    			}
    		}
    	})
    }
	
	function checkPond(){
		var pondName = $('#pondName').val();
    	var actionValue = document.getElementById("myForm").action;
    	var indexOfId = actionValue.indexOf("id=");
    	var pondID = 0;
    	if(indexOfId>=0){
    		pondID = actionValue.substring(indexOfId+3);
    	} 
    	

    	var data = {
    			id : pondID,
    			name : pondName,
    	};
		$.ajax({
			type : "POST",
    		url: "PondCheck",
    		data: data,
    		success : function(responseText){
				console.log(responseText);
    			if(responseText != ""){
    				$("#showMessage").removeClass("hiddenField");
    				$("#showMessage").text(responseText);
    				$("button").attr("disabled", true);
    			} else {
    				$("#showMessage").addClass("hiddenField");
    				$("button").removeAttr("disabled");
    			}
    		}
    	});
	}
	
	$('#minSize').focusout(function(){
		checkMinSize();
		checkSize();
	});
	
	$('#maxSize').focusout(function(){
		checkMaxSize();
		checkSize();
	});
	
	function checkMinSize(){
		var a = $('#minSize').val();
		if (a != ""){
			var aInt = parseFloat(a);
			if (aInt < 0){
				$("#fishSizeMesage").removeClass("hiddenField");
				$("#fishSizeMesage").text("Trọng lượng nhập vào phải lớn hơn 0");
				$("button").attr("disabled", true);
			} else {
				$("#fishSizeMesage").addClass("hiddenField");
				$("button").removeAttr("disabled");
			}
		}
	}
	
	function checkMaxSize(){
		var b = $('#maxSize').val();
		if (b != ""){
			var bInt = parseFloat(b);
			if (bInt < 0){
				$("#fishSizeMesage").removeClass("hiddenField");
				$("#fishSizeMesage").text("Trọng lượng nhập vào phải lớn hơn 0");
				$("button").attr("disabled", true);
				
			} else {
				$("#fishSizeMesage").addClass("hiddenField");
				$("button").removeAttr("disabled");
				
			}
		}
	}
	
	function checkSize(){
		var a = $('#minSize').val();
		var b = $('#maxSize').val();
		
		if (a != "" && b != ""){
			var aInt = Number(a);
			var bInt = Number(b);
			if (aInt >= bInt){
				$("#fishSizeMesage").removeClass("hiddenField");
				$("#fishSizeMesage").text("Trọng lượng tối thiểu phải nhỏ hơn trọng lượng tối đa");
				$("button").attr("disabled", true);
			} else {
				$("#fishSizeMesage").addClass("hiddenField");
				$("button").removeAttr("disabled");
			}
		} else if (a == "" && b == ""){
			$("#fishSizeMesage").addClass("hiddenField");
			$("button").removeAttr("disabled");
		}
		
	}
	
	$('#amount').focusout(function(){
		var a = Number($('#amount').val());
		if (a < 0){
			$("#fishAmountMessage").removeClass("hiddenField");
			$("#fishAmountMessage").text("Số lượng nhập không phù hợp");
			$("button").attr("disabled", true);
		} else {
			$("#fishAmountMessage").addClass("hiddenField");
			$("button").removeAttr("disabled");
		}
	});
});		