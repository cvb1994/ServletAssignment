var name;

function getPopup() {
	name = document.getElementById("popupname").value;
}

function openForm() {
	if (name!="") {
		document.getElementById("myForm1").style.display = "block";
	}
	setTimeout(function(){
		document.getElementById("myForm1").style.display = "none";
	},3000);
}

function closeForm() {
  document.getElementById("myForm1").style.display = "none";
}
