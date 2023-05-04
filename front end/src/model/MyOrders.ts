export default interface myOrders {
    amount: number,
    chefEmail: string,
    chefGender: string
    chefPhoneNumber: number | null
    chefTier: number | null
    cuisine: string
    foodPreference: string
    mealType: string
    name: string
    noOfPeople: number
    orderId: number
    orderStatus: string,
    occasion : string,
    dateOfOrder : Date
}