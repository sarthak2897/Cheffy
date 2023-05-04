// export default interface Customer{
//     "name": string,
//     "email": string,
//     "gender": string,
//     "phoneNumber": number,
//     "userType":string,
//     "password":string,
//     "verifyPassword":string,
//     "address":{
//         "addressLine1":string,
//         "addressLine2":string,
//         "city":string,
//         "state":string,
//         "pincode":number,
//         "latitude":string,
//         "longitude":string
//     }
// }

export default interface Customer{
  
    address:{
        "addressLine1":string,
        "addressLine2":string,
        "city":string,
        "pinCode":number,
        "latitude":number | undefined,
        "longitude":number | undefined,
        "state":string
    },
    userDetailDTO: {
        "email": string,
        "gender": string,
        "name": string,
        "phoneNumber": number,
        "userType":string,
        "password":string,
        "verifyPassword":string
        }
}
