import {bookingCache} from "@/app/services/bookingCache";

export interface Booking {
    id: string;
    email: string;
    restaurant: string;
    week: string;
    dayIndex: number;
    status: 'CONFIRMED' | 'CANCELLED';
    createdAt: string;
}
const API_BASE_URL = 'http://localhost/api/bookings';

export const bookingService = {
    createBooking: async (email: string, restaurant: string, week: string, dayIndex: number): Promise<Booking> => {
        try {
            const response = await fetch(API_BASE_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    email,
                    restaurant,
                    week,
                    dayIndex,
                    status: 'CONFIRMED',
                    createdAt: new Date().toISOString()
                })
            });

            if (!response.ok) {
                throw new Error('Failed to create booking');
            }

            const booking = await response.json();
            bookingCache.delete(`bookings_${email}`); // Invalidate cache
            return booking;
        } catch (error) {
            console.error('Error creating booking:', error);
            throw error;
        }
    },

    getBookings: async (email: string): Promise<Booking[]> => {
        const cacheKey = `bookings_${email}`;
        const cached = bookingCache.get<Booking[]>(cacheKey);

        if (cached) {
            return cached;
        }

        try {
            const response = await fetch(`${API_BASE_URL}?email=${encodeURIComponent(email)}`, {
                method: 'GET',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                }
            });

            if (!response.ok) {
                const errorText = await response.text();
                console.error('Server response:', errorText);
                throw new Error('Failed to fetch bookings');
            }

            const bookings = await response.json();
            bookingCache.set(cacheKey, bookings, 300000); // Cache for 5 minutes
            return bookings;
        } catch (error) {
            console.error('Error fetching bookings:', error);
            throw error;
        }
    },

    cancelBooking: async (bookingId: string, email: string): Promise<void> => {
        try {
            const response = await fetch(`${API_BASE_URL}/${bookingId}/cancel?email=${encodeURIComponent(email)}`, {
                method: 'PUT',
                headers: {
                    'Accept': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error('Failed to cancel booking');
            }

            bookingCache.delete(`bookings_${email}`); // Invalidate cache
        } catch (error) {
            console.error('Error cancelling booking:', error);
            throw error;
        }
    }
};