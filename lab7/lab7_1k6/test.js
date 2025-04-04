import http from "k6/http";

const BASE_URL = __ENV.BASE_URL || "http://localhost:3333";


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

• How long did the API call take?
 - NA: 1.3 ms
 - WA: 151.66 ms

• How many requests were made?
  - NA: 1
  - WA: 1

• How many requests failed? (i.e., whose HTTP status code was not 200)
  - NA: 1
  - WA: 0
*/