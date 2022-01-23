let editBtn = document.getElementById("edit_btn");
let saveBtn = document.getElementById("save_btn");
editBtn.addEventListener("click", () => {
    showSaveBtnHideEditBtn()
});

function showSaveBtnHideEditBtn(){
    if(edit_btn.innerText  == "Enable edit"){
        save_btn.removeAttribute("disabled");
        edit_btn.innerText = "Cancel";
        edit_btn.classList.remove("btn-primary");
        edit_btn.classList.add("btn-secondary");
        enableEditableFields();
    }else{
        save_btn.setAttribute("disabled", "true");
        edit_btn.innerText = "Enable edit";
        edit_btn.classList.add("btn-primary");
        edit_btn.classList.remove("btn-secondary");
        disableEditableFields();
    }

}

function enableEditableFields(){
    document.getElementById("first_name").removeAttribute("disabled");
    document.getElementById("last_name").removeAttribute("disabled");
    document.getElementById("address").removeAttribute("disabled");
    document.getElementById("phone_number").removeAttribute("disabled");
    document.getElementById("date_birth").removeAttribute("disabled");
    document.getElementById("email").removeAttribute("disabled");

}

function disableEditableFields(){
    document.getElementById("first_name").setAttribute("disabled", "true");
    document.getElementById("last_name").setAttribute("disabled", "true");
    document.getElementById("address").setAttribute("disabled", "true");
    document.getElementById("phone_number").setAttribute("disabled", "true");
    document.getElementById("date_birth").setAttribute("disabled", "true");
    document.getElementById("email").setAttribute("disabled", "true");
}