let sidebar = document.querySelector(".sidebar");
  let closeBtn = document.querySelector("#btn");
  let searchBtn = document.querySelector(".fa-search");

  closeBtn.addEventListener("click", ()=>{
    sidebar.classList.toggle("open");
    menuBtnChange();//calling the function(optional)
  });

  // following are the code to change sidebar button(optional)
  function menuBtnChange() {
   if(sidebar.classList.contains("open")){
     closeBtn.classList.replace("fa-menu", "fa-menu-alt-right");//replacing the iocns class
   }else {
     closeBtn.classList.replace("fa-menu-alt-right","fa-menu");//replacing the iocns class
   }
  }