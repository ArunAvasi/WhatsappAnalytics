// let interestRate=0.3;
// interestRate=1
// console.log(interestRate);
//
// let firstName;
//
// let person = {
//     name:'Mosh',
//     age:30
//
// };
//
// //Dot Notation
//
// person.name='John';
//
// console.log(person.name);
//
// //Bracket Notation
//
// person['name']='Mary';
//
// console.log(person.name);
//
// let selection = ['red','blue'];
// console.log(selection);
//
// //Functions
// //name is not accessable outside of the function
// function greet(name,lastName)
// {
//     console.log('Hello '+name+' '+lastName);
//
// }
//
// greet('John','smith');
// greet('Mary','jane');
//
// function square(number)
// {
//     return number*number;
// }
// let number = square(2);
// console.log(number);
//
// //const mean the variable is constant
// // You would replace 'let' with constant keyword
// //console.log is a method that outputs a message to the web console
// //String, number, boolean literals can be used
// // In javascript, you can change the datatype for variables at runtime

function ValidateFileUpload(ID) {
    var fuData = ('#' + ID);
    var FileUploadPath = fuData[0].value;

    //To check if user upload any file
    if (FileUploadPath == '') {

    } else {
        var Extension = FileUploadPath.substring(
            FileUploadPath.lastIndexOf('.') + 1).toLowerCase();

        //The file uploaded is an image

        if (Extension == "txt") {
            var file = $('#' + ID)[0].files[0];
            var filename = $('#' + ID)[0].files[0].name;
            var blob = new Blob([file]);
            var url = URL.createObjectURL(blob);

            $(this).attr({ 'download': FileUploadPath, 'href': url });
            filename = "";
        }

        //The file upload is NOT an image
        else {
            alert("Document is not the correct format");
        }
    }
}