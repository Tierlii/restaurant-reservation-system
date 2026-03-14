import { useState } from "react";
import FilterPanel from "./components/FilterPanel";
import RestaurantLayout from "./components/RestaurantLayout";
import { createReservation, searchTables } from "./api/api";

import type {
  SearchRequest,
  TableAvailability,
  SearchResponse,
  ReservationRequest,
} from "./types/reservation";

function App() {
  const [tables, setTables] = useState<TableAvailability[]>([]);
  const [selectedTableId, setSelectedTableId] = useState<number | null>(null);
  const [lastSearchRequest, setLastSearchRequest] = useState<SearchRequest | null>(null);

  const [isSearching, setIsSearching] = useState(false);
  const [isReserving, setIsReserving] = useState(false);

  const [successMessage, setSuccessMessage] = useState<string>("");
  const [errorMessage, setErrorMessage] = useState<string>("");

  const handleSearch = async (request: SearchRequest) => {
    try {
      setIsSearching(true);
      setErrorMessage("");
      setSuccessMessage("");

      const response: SearchResponse = await searchTables(request);

      setTables(response.tables);
      setLastSearchRequest(request);

      if (response.recommendedTableId) {
        setSelectedTableId(response.recommendedTableId);
      } else {
        setSelectedTableId(null);
      }
    } catch (error) {
      console.error("Search failed:", error);
      setErrorMessage("Failed to search available tables.");
      setTables([]);
      setSelectedTableId(null);
    } finally {
      setIsSearching(false);
    }
  };

  const handleSelectTable = (table: TableAvailability) => {
    if (!table.available && !table.recommended) {
      return;
    }

    setSelectedTableId(table.id);
    setSuccessMessage("");
    setErrorMessage("");
  };

  const handleReserveTable = async () => {
    if (!selectedTable || !lastSearchRequest) {
      return;
    }

    try {
      setIsReserving(true);
      setErrorMessage("");
      setSuccessMessage("");

      const request: ReservationRequest = {
        tableId: selectedTable.id,
        date: lastSearchRequest.date,
        time: lastSearchRequest.time,
        partySize: lastSearchRequest.partySize,
      };

      const response = await createReservation(request);

      setSuccessMessage(response.message);

      const refreshedResponse: SearchResponse = await searchTables(lastSearchRequest);
      setTables(refreshedResponse.tables);

      if (refreshedResponse.recommendedTableId) {
        setSelectedTableId(refreshedResponse.recommendedTableId);
      } else {
        setSelectedTableId(null);
      }
    } catch (error) {
      console.error("Reservation failed:", error);
      setErrorMessage("Failed to create reservation.");
    } finally {
      setIsReserving(false);
    }
  };

  const selectedTable = tables.find((table) => table.id === selectedTableId);

  return (
    <div className="app-container">
      <header className="app-header">
        <h1>Smart Restaurant Reservation</h1>
        <p>Search for available tables and get the best recommendation.</p>
      </header>

      <main className="app-grid">
        <aside className="left-column">
          <FilterPanel onSearch={handleSearch} isLoading={isSearching} />

          <section className="info-panel">
            <h2 className="panel-title">Selected table</h2>

            {selectedTable ? (
              <div className="selected-table-card">
                <p>
                  <strong>Name:</strong> {selectedTable.name}
                </p>

                <p>
                  <strong>Capacity:</strong> {selectedTable.capacity}
                </p>

                <p>
                  <strong>Zone:</strong> {selectedTable.zone}
                </p>

                <p>
                  <strong>Status:</strong>{" "}
                  {selectedTable.recommended
                    ? "Recommended"
                    : selectedTable.available
                    ? "Available"
                    : "Occupied"}
                </p>

                {typeof selectedTable.score === "number" && (
                  <p>
                    <strong>Score:</strong> {selectedTable.score}
                  </p>
                )}

                <button
                  type="button"
                  className="reserve-button"
                  onClick={handleReserveTable}
                  disabled={!selectedTable.available || isReserving || !lastSearchRequest}
                >
                  {isReserving ? "Reserving..." : "Reserve table"}
                </button>
              </div>
            ) : (
              <p className="placeholder-text">
                Run a search and click an available table on the map.
              </p>
            )}
          </section>

          {(successMessage || errorMessage) && (
            <section className="info-panel">
              <h2 className="panel-title">Reservation status</h2>

              {successMessage && <p className="success-message">{successMessage}</p>}
              {errorMessage && <p className="error-message">{errorMessage}</p>}
            </section>
          )}
        </aside>

        <section className="right-column">
          <RestaurantLayout
            tables={tables}
            selectedTableId={selectedTableId}
            onSelectTable={handleSelectTable}
          />
        </section>
      </main>
    </div>
  );
}

export default App;
