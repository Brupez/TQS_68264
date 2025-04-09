import { API_BASE_URL } from "@/app/config/config";

interface WeatherInfo {
  temperature: number;
  weatherCondition: string;
  humidity: number;
}

interface MealResponse {
  id: number;
  name: string;
  description: string;
  date: string;
  restaurantName: string;
  temperature: number;
  weatherCondition: string;
  humidity: number;
}

interface MealOption {
  soup: string;
  meat: string;
  fish: string;
  temperature: number;
  weatherCondition: string;
  humidity: number;
}

interface WeekMeals {
  [weekNumber: string]: MealOption[];
}

interface Restaurant {
  title: string;
  meals: { [key: string]: MealOption[] }[];
}


export const restaurantService = {
  getRestaurantMeals: async (restaurantName: string, weekNumber: string): Promise<Restaurant> => {
    try {
      console.log(`Fetching meals for ${restaurantName} week ${weekNumber}`);
      const response = await fetch(`${API_BASE_URL}/api/restaurants/${restaurantName}/${weekNumber}/meals`, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
        },
      });

      if (!response.ok) {
        const errorText = await response.text();
        console.error('Server response:', errorText);
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data: { [key: string]: MealResponse[] } = await response.json();
      console.log('Received data:', data);

      const transformedMeals = data[weekNumber].map(meal => {
        const [soup, meat, fish] = meal.description
            .split(',')
            .map(item => item.trim().split(': ')[1]);

        return {
          soup,
          meat,
          fish,
          temperature: meal.temperature,
          weatherCondition: meal.weatherCondition,
          humidity: meal.humidity
        };
      });


      return {
          title: restaurantName,
          meals: [{
            [weekNumber]: transformedMeals
          }]
      };

    } catch (error) {
      console.error('Error fetching restaurant data:', error);
      throw error;
    }
  },
  
  getAllRestaurants: async (weekNumber: string): Promise<Restaurant[]> => {
    const restaurantNames = ['Castro', 'Santiago'];
    console.log('Fetching restaurants for week:', weekNumber);

    try {
      const restaurants = await Promise.all(
          restaurantNames.map(name =>
              restaurantService.getRestaurantMeals(name, weekNumber)
                  .catch(error => {
                    console.error(`Failed to fetch meals for ${name}:`, error);
                    return null;
                  })
          )
      );

      const validRestaurants = restaurants.filter((r): r is Restaurant => r !== null);
      if (validRestaurants.length === 0) {
        throw new Error('No restaurant data available');
      }

      return validRestaurants;
    } catch (error) {
      console.error('Error fetching all restaurants:', error);
      throw error;
    }
  }
}; 