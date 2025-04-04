import http from "k6/http";

const BASE_URL = __ENV.BASE_URL || "http://localhost:3333";

export const options = {
    stages: [
// ramp up from 0 to 20 VUs over the next 5 seconds
        { duration: '5s', target: 20 },
// run 20 VUs over the next 10 seconds
        { duration: '10s', target: 20 },
// ramp down from 20 to 0 VUs over the next 5 seconds
        { duration: '5s', target: 0 },
    ],
};


export default function () {
    let restrictions = {
        maxCaloriesPerSlice: 500,
        mustBeVegetarian: false,
        excludedIngredients: ["pepperoni"],
        excludedTools: ["knife"],
        maxNumberOfToppings: 6,
        minNumberOfToppings: 2,
    };
    let res = http.post(`${BASE_URL}/api/pizza`, JSON.stringify(restrictions), {
        headers: {
            "Content-Type": "application/json",
            "X-User-ID": 23423,
            "Authorization": "token abcdef0123456789",
        },
    });
    // console.log(`${res.json().pizza.name} (${res.json().pizza.ingredients.length} ingredients)`);
}
/*

• How long did the API calls take on average, minimum and maximum?
 -  avg=147.51ms min=5.15ms max=744.84ms

• How many requests were made?
    -  2065 requests

• How many requests failed? (i.e., whose HTTP status code was not 200)
    -  0 requests failed

 */