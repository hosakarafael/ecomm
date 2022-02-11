let editBuyerBtn = document.getElementById("edit_buyer_btn");
editBuyerBtn.addEventListener("click", () => {
    enableDisableEditBuyer()
});

let editShopBtn = document.getElementById("edit_shop_btn");
editShopBtn.addEventListener("click", () => {
    enableDisableEditShop()
});

function enableDisableEditBuyer(){
    const fields = ["buyer_first_name", "buyer_last_name", "buyer_address", "buyer_phone_number", "buyer_date_birth", "buyer_email"];
    if(editBuyerBtn.innerText  == "Enable edit"){
        enableSaveBtn(document.getElementById("save_buyer_btn"));
        changeBtnEditToCancel(editBuyerBtn);
        enableFields(fields);
    }else{
        disableSaveBtn(document.getElementById("save_buyer_btn"));
        changeBtnCancelToEdit(editBuyerBtn);
        disableFields(fields);
    }
}

function enableDisableEditShop(){
    const fields = ["shop_name","shop_description","shop_address","shop_phone_number"];
    if(editShopBtn.innerText  == "Enable edit"){
        enableSaveBtn(document.getElementById("save_shop_btn"));
        changeBtnEditToCancel(editShopBtn);
        enableFields(fields);
    }else{
        disableSaveBtn(document.getElementById("save_shop_btn"));
        changeBtnCancelToEdit(editShopBtn);
        disableFields(fields);
    }
}

function enableSaveBtn(saveBtn){
    saveBtn.removeAttribute("disabled");
}

function disableSaveBtn(saveBtn){
    saveBtn.setAttribute("disabled", "true");
}

function changeBtnEditToCancel(editBtn){
    editBtn.innerText = "Cancel";
    editBtn.classList.remove("btn-primary");
    editBtn.classList.add("btn-secondary");
}

function changeBtnCancelToEdit(editBtn){
    editBtn.innerText = "Enable edit";
    editBtn.classList.add("btn-primary");
    editBtn.classList.remove("btn-secondary");
}

function enableFields(fields){
    for (const fieldName of fields) {
      let field = document.getElementById(fieldName);
      field.removeAttribute("disabled");
      field.classList.add("highlight");
    }
}

function disableFields(fields){
   for (const fieldName of fields) {
        let field = document.getElementById(fieldName);
         field.setAttribute("disabled", "true");
         field.classList.remove("highlight");

    }
}