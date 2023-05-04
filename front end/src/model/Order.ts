export default interface Order
  
    {
        "amount" : number;
        "chefTier": number,
        "cuisine": string,
        "customerID": number,
        "date": string,   // date should not be past and after 2 months from today
        "foodPreference": string,
        "genderPreference": string, 
        "ingredients": boolean,
        "mealType": string,
        "noOfHobs": number,
        "noOfPeople": number,
        "occasion": string, // Occasion, Fullweek ,Month
        "oven": boolean,
        "time": string,  // 30 mins or 00 and seconds should always be 00
        "typeOfHob": string
      }


