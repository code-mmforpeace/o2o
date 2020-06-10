$(function () {
   var getPersonInfoUrl = '/userController/getPersonInfo';
   getPersonInfo();
   function getPersonInfo() {
       $.getJSON(getPersonInfoUrl,function (data) {
           if(data.success){
               var personinfo = data.personinfo;
                $('#personinfo-name').val(personinfo.name);
                $('#personinfo-birthday').val(new Date(personinfo.birthday).Format("yyyy-MM-dd"));
                $('#personinfo-phone').val(personinfo.phone);
                $('#personinfo-email').val(personinfo.email);
           }
       })
   }
});