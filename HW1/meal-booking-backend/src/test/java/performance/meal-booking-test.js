import http from 'k6/http';
import { check, sleep } from 'k6';

// Test configuration
export const options = {
    stages: [
        { duration: '30s', target: 10 },
        { duration: '1m', target: 20 },
        { duration: '30s', target: 0 },
    ],
    thresholds: {
        http_req_duration: ['p(95)<500'],  // 95% of requests < 500ms
        http_req_failed: ['rate<0.01'],    // Error rate < 1%
    },
};

export default function () {
    // 1. Book a meal
    const bookResponse = http.post('http://localhost/api/bookings', JSON.stringify({
        restaurant: "Castro",
        week: "13",
        dayIndex: 2,
        email: `user_${__VU}@test.com`,
        status: "CONFIRMED"
    }), {
        headers: { 'Content-Type': 'application/json' },
    });

    check(bookResponse, {
        'Booking created successfully': (r) => r.status === 201,
    });

    // 2. Get booking by email
    const getResponse = http.get(`http://localhost/api/bookings?email=user_${__VU}@test.com`);

    check(getResponse, {
        'Retrieved booking successfully': (r) => r.status === 200,
    });

    // 3. Cancel booking
    const bookings = getResponse.json();
    if (bookings && bookings.length > 0) {
        const cancelResponse = http.put(
            `http://localhost/api/bookings/${bookings[0].id}/cancel?email=user_${__VU}@test.com`
        );

        check(cancelResponse, {
            'Booking cancelled successfully': (r) => r.status === 200,
        });
    }
    sleep(1);
}