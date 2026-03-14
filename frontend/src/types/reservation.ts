export type Zone =
  | "MAIN_HALL"
  | "TERRACE"
  | "PRIVATE_ROOM";

export type Preference =
  | "WINDOW"
  | "QUIET"
  | "NEAR_KIDS_AREA"
  | "ACCESSIBLE";

export interface SearchRequest {
  date: string;
  time: string;
  partySize: number;
  zone?: Zone;
  preferences?: Preference[];
}

export interface TableAvailability {
  id: number;
  name: string;
  capacity: number;
  zone: Zone;

  x: number;
  y: number;

  windowSeat: boolean;
  quiet: boolean;
  nearKidsArea: boolean;
  accessible: boolean;

  available: boolean;
  recommended: boolean;
  score: number | null;
}

export interface SearchResponse {
  tables: TableAvailability[];
  recommendedTableId: number | null;
}

export interface ReservationRequest {
  tableId: number;
  date: string;
  time: string;
  partySize: number;
}

export interface ReservationResponse {
  reservationId: number;
  tableId: number;
  reservationStart: string;
  reservationEnd: string;
  message: string;
}