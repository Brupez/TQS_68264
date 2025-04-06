#!/bin/bash

# Base URL
BASE_URL="http://localhost:8081/api/bookings"

# Create a new booking
echo "Creating a new booking..."
CREATE_RESPONSE=$(curl -s -X POST "$BASE_URL" -H "Content-Type: application/json" -d '{
  "email": "user@example.com",
  "restaurant": "Restaurant Name",
  "week": "2024-W15",
  "dayIndex": 1,
  "status": "CONFIRMED",
  "createdAt": "2024-04-08T10:00:00"
}')

echo "Create response: $CREATE_RESPONSE"

# Extract the booking ID from the create response
BOOKING_ID=$(echo $CREATE_RESPONSE | jq -r '.id')
echo "Created booking ID: $BOOKING_ID"

# Get all bookings by email
echo "Getting all bookings for email user@example.com..."
GET_RESPONSE=$(curl -s -X GET "$BASE_URL?email=user@example.com")
echo "Get response: $GET_RESPONSE"

# Update (cancel) the booking
echo "Cancelling the booking with ID $BOOKING_ID..."
CANCEL_RESPONSE=$(curl -s -X PUT "$BASE_URL/$BOOKING_ID/cancel?email=user@example.com")
echo "Cancel response: $CANCEL_RESPONSE"

# Delete the booking
echo "Deleting the booking with ID $BOOKING_ID..."
DELETE_RESPONSE=$(curl -s -X DELETE "$BASE_URL/$BOOKING_ID/")
echo "Delete response: $DELETE_RESPONSE"