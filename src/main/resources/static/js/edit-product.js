var loadFile = function(event) {
	var image = document.getElementById('output');
	image.src = URL.createObjectURL(event.target.files[0]);
};

document.getElementById("price").onkeypress = function(event){
    if(event.which == 46){
        if(document.getElementById("price").value.includes(".")){
            return false;
        }
    }else{
        if(event.which < 48 || event.which > 57){
            return false;
        }
    }
}

document.getElementById("price").onkeyup = function(event){
        var text = document.getElementById("price");
         if(text.value.includes(".")){
            var splitNumber = text.value.split('.');
            if(splitNumber[1].length > 2){
               text.value = text.value.slice(0, splitNumber[0].length + splitNumber[1].length);
            }
        }
}