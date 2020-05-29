function confirmDelete(location) {
    var result = confirm("Want to delete?");
    console.log(result);
    if (result) {
        return true;
    }else{
       return false;
    }
}