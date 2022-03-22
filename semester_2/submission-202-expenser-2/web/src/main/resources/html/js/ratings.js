dropList = document.getElementById('list');

dropList.addEventListener('change', () => {

      var version = navigator.appVersion;

      if (dropList.value!="no_page"){
        if (version.indexOf("MSIE") >=1){
            window.location.href = dropd.value;
        }
        else{
            window.open(dropList.value,target = "_self");
        }
      }
    }
);