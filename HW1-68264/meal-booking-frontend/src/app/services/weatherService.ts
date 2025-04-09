import { API_BASE_URL } from '../config/config';

export interface Weather {
    temperature: number;
    humidity: number;
    condition: string;
}

export const weatherService = {
    getWeather: async (city: string): Promise<Weather> => {
        try {
            const response = await fetch(`${API_BASE_URL}/weather?city=${city}`);
            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
            return await response.json();
        } catch (error) {
            console.error('Error fetching weather:', error);
            throw error;
        }
    }
};