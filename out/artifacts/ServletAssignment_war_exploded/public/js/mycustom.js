/**
 * 
 */
$(document).ready(function (){
	var numberOfType = document.getElementById("fishList").length;
	
	var tempNewFish = new Array(numberOfType);
	
	$('#addFish').on('click',add);
	var rowCount = 0;
	
	function add(){
		var fishName = $("#fishList").val();
		var amount = $("#amount").val();
		
		var newFishObject = {
				'name' : fishName,
				'amount':amount
		}
		tempNewFish[rowCount] = newFishObject;
		tableDisplay();
		objectToString();
		rowCount++;
		
		//xóa các phần tử đã được thêm mới trong select
		var listFish = document.getElementById("fishList");
		for (var i =0; i < listFish.length; i++){
			if (listFish.options[i].value == fishName){
				listFish.remove(i);
			}
		}
	}
	
	function tableDisplay(){
   		$(".tempRow").remove();
   		var no = 1;
   		for (var i = 0;i<numberOfType;i++){
   			if (tempNewFish[i] != null){
   				var row = tempNewFish[i];
   				generateRow(no, row.name, row.amount);
   				no++;
   			}
   		}
   	}
	
	function generateRow(no, name, amount){
		var newRow = document.createElement("tr");
		newRow.setAttribute("class","tempRow");
		document.getElementById("AddFishTable").appendChild(newRow);
		var noData = document.createElement("td");
		noData.innerHTML = no;
		var newNameData = document.createElement("td");
		newNameData.innerHTML = name;
		var newAmountData = document.createElement("td");
		newAmountData.innerHTML = amount;
		var deleteButton = document.createElement("button");
		deleteButton.setAttribute("class","btn btn-dark");
		deleteButton.setAttribute("id",no);
		deleteButton.setAttribute("onClick","deleteFish("+no+")");
		deleteButton.setAttribute("type","button");
		deleteButton.innerHTML = "Delete";
		newRow.appendChild(noData);
		newRow.appendChild(newNameData);
		newRow.appendChild(newAmountData);
		newRow.appendChild(deleteButton);
	}
	
	function deleteFish(id){
		var rowDeleted = id-1;
		var nameDeleted = tempNewFish[rowDeleted].name;
		tempNewFish.splice(rowDeleted,1);
		var newOption = document.createElement("option");
		newOption.setAttribute("value",nameDeleted);
		newOption.innerHTML = nameDeleted;
		var listFish = document.getElementById("fishList");
		listFish.appendChild(newOption);
   		tableDisplay();
   		rowCount--;
   		objectToString();
   	}
	
	function objectToString(){
		var objectString = "";
		for(var i =0; i < tempNewFish.length; i++){
			if(tempNewFish[i] != null){
				if(i==0){
					var stringPart = tempNewFish[i].name +"-"+tempNewFish[i].amount;
        			objectString = objectString.concat(stringPart);
				} else {
					var stringPart = ","+tempNewFish[i].name +"-"+tempNewFish[i].amount;
        			objectString = objectString.concat(stringPart);
				}
			}
		}
		document.getElementById("fishAdded").setAttribute("value",objectString);
	}
})