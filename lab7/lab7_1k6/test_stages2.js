import http from "k6/http";
import { check } from "k6";

const BASE_URL = __ENV.BASE_URL || "http://localhost:3333";


export const options = {
    stages: [
// ramp up from 0 to 20 VUs over the next 30s seconds
        { duration: '30s', target: 120 },
// run 20 VUs over the next 30 seconds
        { duration: '30s', target: 120 },
// ramp down from 20 to 0 VUs over the next 30 seconds
        { duration: '30s', target: 0 },
    ],

    thresholds: {
        "http_req_duration": [
            { threshold: "p(95)<1100"},
        ],
        "http_req_failed": [
            { threshold: "rate<0.01"},
        ],
        "checks":
            ["rate>0.98"],
    },
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

    check(res, {
        'is status 200': (r) => r.status === 200,
        'body size is lower of 1k': (r) => r.body.length < 1000,
        });

    // console.log(`${res.json().pizza.name} (${res.json().pizza.ingredients.length} ingredients)`);
}

/*

 How to run this test with k6 dashboard:
 K6_WEB_DASHBOARD=true k6 run test_stages_thresholds_checks.js
 */