"use client";

import React, { useState, useEffect } from 'react';
import { Booking ,bookingService} from "@/app/services/bookingService";

const DayNames = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"];


export const BookingsList: React.FC = () => {
    const [bookings, setBookings] = useState<Booking[]>([]);
    const [email, setEmail] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);


    const fetchBookings = async (email: string) => {
        try {
            setLoading(true);
            setError('');
            const data = await bookingService.getBookings(email);
            setBookings(data);
        } catch (err) {
            setError('Failed to fetch bookings');
        } finally {
            setLoading(false);
        }
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (email) {
            fetchBookings(email);
        }
    };

    const handleCancel = async (bookingId: string) => {
        try {
            setLoading(true);
            await bookingService.cancelBooking(bookingId, email);
            await fetchBookings(email);
            setError(null);
        } catch (err) {
            setError('Failed to cancel booking');
        } finally {
            setLoading(false);
        }
    }

    return (
        <div className="max-w-2xl mx-auto mt-8 p-6 bg-white rounded-xl shadow-sm border">
            <h2 className="text-2xl font-bold text-gray-800 mb-6">My Bookings</h2>

            <form onSubmit={handleSubmit} className="mb-6">
                <div className="flex gap-3">
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        placeholder="Enter your email"
                        className="flex-1 px-4 py-2 rounded-lg border-2 border-gray-200 focus:border-blue-500 focus:ring-0"
                    />
                    <button
                        type="submit"
                        disabled={!email || loading}
                        className="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700
              disabled:bg-gray-300 disabled:cursor-not-allowed transition-colors"
                    >
                        {loading ? 'Loading...' : 'Search'}
                    </button>
                </div>
            </form>

            {error && (
                <div className="p-4 mb-4 text-red-700 bg-red-100 rounded-lg">
                    {error}
                </div>
            )}

            {bookings.length > 0 ? (
                <div className="space-y-4">
                    {bookings.map((booking) => (
                        <div
                            key={booking.id}
                            className="p-4 border rounded-lg hover:shadow-sm transition-shadow"
                        >
                            <div className="flex justify-between items-start">
                                <div>
                                    <h3 className="font-medium text-gray-800">{booking.restaurant}</h3>
                                    <p className="text-sm text-gray-500">
                                        Week {booking.week}, {DayNames[booking.dayIndex]}
                                    </p>
                                </div>
                                <span className={`
                  px-3 py-1 text-sm rounded-full
                  ${booking.status === 'CONFIRMED' ? 'bg-green-100 text-green-700' : ''}
                  ${booking.status === 'CANCELLED' ? 'bg-red-100 text-red-700' : ''}
                `}>
                  {booking.status}
                </span>
            </div>
            <div className="mt-2 text-sm text-gray-500">
                Booked on: {new Date(booking.createdAt).toLocaleDateString()}
            </div>
            {booking.status === 'CONFIRMED' && (
                <button
                    onClick={() => handleCancel(booking.id)}
                    disabled={loading}
                    className="mt-3 w-full px-4 py-2 text-sm font-medium text-red-600 bg-red-50
                    rounded-lg hover:bg-red-100 disabled:opacity-50 disabled:cursor-not-allowed
                    transition-colors"
                >
                    Cancel Booking
                </button>
            )}
                        </div>
                    ))}
                </div>
            ) : (
                email && !loading && (
                    <div className="text-center py-8 text-gray-500">
                        No bookings found for this email
                    </div>
                )
            )}
        </div>
    );
};