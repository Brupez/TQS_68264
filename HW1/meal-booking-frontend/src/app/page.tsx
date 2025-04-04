"use client";

import React, { useState, useEffect } from "react";
import { BookingsList } from "./components/BookingsList";
import { bookingService } from "@/app/services/bookingService";
import { restaurantService } from "@/app/services/restaurantService";

type MealOption = {
    soup: string;
    meat: string;
    fish: string;
    temperature?: number;
    weatherCondition?: string;
    humidity?: number;
};

interface Restaurant {
    title: string;
    meals: Array<{
        [week: string]: MealOption[];
    }>;
}

interface RestaurantCardProps {
    title: string;
    meals: Array<{
        [week: string]: MealOption[];
    }>;
}

const DayNames = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"];

const WeatherInfo: React.FC<{
    temperature?: number;
    weatherCondition?: string;
    humidity?: number;
}> = ({ temperature, weatherCondition, humidity }) => (
    <div className="inline-flex gap-6 px-4 py-2 bg-gray-50 rounded-full text-sm text-gray-600">
    <span title="Temperature" className="flex items-center gap-1">
      <span className="text-amber-500">🌡️</span> {temperature}°C
    </span>
        <span title="Weather" className="flex items-center gap-1">
      <span className="text-blue-400">☁️</span> {weatherCondition}
    </span>
        <span title="Humidity" className="flex items-center gap-1">
      <span className="text-blue-500">💧</span> {humidity}%
    </span>
    </div>
);

const DayCard: React.FC<{
    dayMeal: MealOption;
    dayName: string;
    onBook: (email: string) => void;
}> = ({ dayMeal, dayName, onBook }) => {
    const [isExpanded, setIsExpanded] = useState(false);
    const [email, setEmail] = useState("");

    return (
        <div className="border rounded-xl mb-3 overflow-hidden bg-white shadow-sm hover:shadow-md transition-shadow">
            <div
                id="day"
                className="flex justify-between items-center px-4 py-3 cursor-pointer bg-gradient-to-r from-gray-50 to-white"
                onClick={() => setIsExpanded(!isExpanded)}
            >
                <h4 className="font-medium text-gray-700">{dayName}</h4>
                <svg
                    className={`w-5 h-5 text-gray-400 transition-transform duration-300 ${isExpanded ? 'rotate-180' : ''}`}
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                >
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 9l-7 7-7-7" />
                </svg>
            </div>

            {isExpanded && (
                <div className="p-4 border-t space-y-4">
                    <div className="flex justify-center">
                        <WeatherInfo
                            temperature={dayMeal.temperature}
                            weatherCondition={dayMeal.weatherCondition}
                            humidity={dayMeal.humidity}
                        />
                    </div>

                    <div className="grid grid-cols-3 gap-4">
                        {(['soup', 'meat', 'fish'] as const).map((type) => (
                            <div key={type} className="bg-gray-50 p-4 rounded-lg">
                                <span className="text-sm font-medium text-gray-500 uppercase tracking-wider">{type}</span>
                                <p className="text-gray-700 mt-1 font-medium">{dayMeal[type]}</p>
                            </div>
                        ))}
                    </div>

                    <div className="pt-4">
                        <input
                            type="email"
                            placeholder="Enter your email to book"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            className="w-full px-4 py-2.5 rounded-lg border-2 border-gray-200 focus:border-blue-500 focus:ring-0 transition-colors"
                            onClick={(e) => e.stopPropagation()}
                        />

                        <button
                            onClick={(e) => {
                                e.stopPropagation();
                                onBook(email);
                                setEmail("");
                            }}
                            disabled={!email}
                            className="mt-3 w-full px-4 py-2.5 text-sm font-medium bg-blue-600 text-white rounded-lg
                hover:bg-blue-700 disabled:bg-gray-300 disabled:cursor-not-allowed transition-colors"
                        >
                            Book Meal
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
};

const WeekCard: React.FC<{ week: string; meals: MealOption[]; title: string }> = ({ week, meals, title }) => {
    const [isExpanded, setIsExpanded] = useState(false);

    const handleBooking = async (dayIndex: number, email: string, restaurant: string) => {
        try {
            const booking = await bookingService.createBooking(email, restaurant, week, dayIndex);
            console.log('Booking created:', booking);
            alert('Booking submitted successfully!');
        } catch (error) {
            console.error('Booking failed:', error);
            alert('Failed to create booking. Please try again.');
        }
    };

    return (
        <div className="border rounded-xl mb-4 overflow-hidden bg-white shadow-sm">
            <div
                id="week"
                className="flex justify-between items-center px-5 py-4 cursor-pointer bg-gradient-to-r from-gray-50 to-white"
                onClick={() => setIsExpanded(!isExpanded)}
            >
                <h3 className="font-semibold text-lg text-gray-800">Week {week}</h3>
                <svg
                    className={`w-5 h-5 text-gray-400 transition-transform duration-300 ${isExpanded ? 'rotate-180' : ''}`}
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                >
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 9l-7 7-7-7" />
                </svg>
            </div>

            {isExpanded && (
                <div className="p-4 border-t bg-gray-50">
                    {meals.map((dayMeal, dayIndex) => (
                        <DayCard
                            key={dayIndex}
                            dayMeal={dayMeal}
                            dayName={DayNames[dayIndex]}
                            onBook={(email) => handleBooking(dayIndex, email, title)}
                        />
                    ))}
                </div>
            )}
        </div>
    );
};

const RestaurantCard: React.FC<RestaurantCardProps> = ({ title, meals }) => (
    <div className="max-w-2xl rounded-xl overflow-hidden shadow-lg bg-white border">
        <div className="px-6 py-5">
            <h2 className="font-bold text-2xl mb-6 text-center text-gray-800 pb-4 border-b">
                {title}
            </h2>
            {meals.map((mealWeek, index) => {
                const week = Object.keys(mealWeek)[0];
                const weekMeals = mealWeek[week];
                return <WeekCard key={index} week={week} meals={weekMeals} title={title} />;
            })}
        </div>
    </div>
);

export default function Home() {
    const [restaurants, setRestaurants] = useState<Restaurant[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    
    useEffect(() => {
        const fetchRestaurants = async () => {
            try {
                setLoading(true);
                const data = await restaurantService.getAllRestaurants("13");
                setRestaurants(data);
                setError(null);
            } catch (err) {
                console.error("Failed to fetch restaurants:", err);
                setError("Failed to load restaurants. Using static data instead.");
                setRestaurants([]);
            } finally {
                setLoading(false);
            }
        };
        
        fetchRestaurants();
    }, []);
    
    if (loading) {
        return <div className="min-h-screen flex items-center justify-center">Loading restaurants...</div>;
    }
    
    return (
        <div className="min-h-screen bg-gray-50 py-12 px-4">
            {error && (
                <div className="mb-4 p-4 bg-red-100 text-red-800 rounded-md">
                    {error}
                </div>
            )}
            <div className="container mx-auto max-w-6xl">
                <div className="grid md:grid-cols-3 gap-8">
                    {restaurants.map((restaurant, index) => (
                        <RestaurantCard key={index} title={restaurant.title} meals={restaurant.meals} />
                    ))}
                    <BookingsList />
                </div>
            </div>
        </div>
    );
}