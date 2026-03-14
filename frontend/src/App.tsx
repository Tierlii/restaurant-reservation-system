import { useState } from "react";
import FilterPanel from "./components/FilterPanel";
import RestaurantLayout from "./components/RestaurantLayout";
import { searchTables } from "./api/api";

import type {
  SearchRequest,
  TableAvailability,
  SearchResponse,
} from "./types/reservation";

function App() {
  const [tables, setTables] = useState<TableAvailability[]>([]);
  const [selectedTableId, setSelectedTableId] = useState<number | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  const handleSearch = async (request: SearchRequest) => {
    try {
      setIsLoading(true);

      const response: SearchResponse = await searchTables(request);

      setTables(response.tables);

      if (response.recommendedTableId) {
        setSelectedTableId(response.recommendedTableId);
      } else {
        setSelectedTableId(null);
      }
    } catch (error) {
      console.error("Search failed:", error);
      alert("Search request failed");
    } finally {
      setIsLoading(false);
    }
  };

  const handleSelectTable = (table: TableAvailability) => {
    setSelectedTableId(table.id);
  };

  const selectedTable = tables.find((t) => t.id === selectedTableId);

  return (
    <div className="app-container">
      <header className="app-header">
        <h1>Smart Restaurant Reservation</h1>
        <p>Search for available tables and get the best recommendation.</p>
      </header>

      <main className="app-grid">
        <aside className="left-column">
          <FilterPanel onSearch={handleSearch} isLoading={isLoading} />

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
              </div>
            ) : (
              <p className="placeholder-text">
                Run a search to see available tables.
              </p>
            )}
          </section>
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
