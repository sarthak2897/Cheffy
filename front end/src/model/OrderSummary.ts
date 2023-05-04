export default interface OrderSummaryModel{
    addressDTO:{
    "addressLine1" : string,
    "addressLine2": string,
    "city": string,
    "latitude": number,
    "longitude": number,
    "pinCode": number,
    "state": string
    }
amountToBePaid: number,
cuisine: string,
foodPreference: string,
mealType: string,
occasion: string,
orderId: string,
totalAmount: number | undefined,
orderDate : Date,
noOfPeople : number,
chefTier : number,
email : string
}