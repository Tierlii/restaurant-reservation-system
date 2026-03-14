import type {
  SearchRequest,
  SearchResponse,
  ReservationRequest,
  ReservationResponse,
} from "../types/reservation";

const BASE_URL = "http://localhost:8080/api";

export async function searchTables(
  request: SearchRequest
): Promise<SearchResponse> {
  const response = await fetch(`${BASE_URL}/search`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(request),
  });

  if (!response.ok) {
    throw new Error("Search request failed");
  }

  return response.json();
}

export async function createReservation(
  request: ReservationRequest
): Promise<ReservationResponse> {
  const response = await fetch(`${BASE_URL}/reservations`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(request),
  });

  if (!response.ok) {
    throw new Error("Reservation failed");
  }

  return response.json();
}