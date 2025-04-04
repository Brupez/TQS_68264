import { API_BASE_URL } from '../config/config';

export interface Stats {
  totalRequests: number;
  cacheHits: number;
  cacheMisses: number;
}

export const statsService = {
  getStats: async (): Promise<Stats> => {
    try {
      const response = await fetch(`${API_BASE_URL}/stats`);
      if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
      return await response.json();
    } catch (error) {
      console.error('Error fetching stats:', error);
      throw error;
    }
  }
};