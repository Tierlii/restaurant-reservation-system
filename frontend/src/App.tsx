import { useState } from "react";
import FilterPanel from "./components/FilterPanel";
import type { SearchRequest } from "./types/reservation";

function App() {
  const [lastSearch, setLastSearch] = useState<SearchRequest | null>(null);

  const handleSearch = (request: SearchRequest) => {
    console.log("Search request:", request);
    setLastSearch(request);
  };

  return (
    <div className="app-container">
      <header className="app-header">
        <h1>Smart Restaurant Reservation</h1>
        <p>
          Search for available tables and get the best recommendation for your
          group.
        </p>
      </header>

      <main className="app-content">
        <FilterPanel onSearch={handleSearch} />

        <section className="placeholder-panel">
          <h2 className="panel-title">Search preview</h2>

          {lastSearch ? (
            <pre className="search-preview">
              {JSON.stringify(lastSearch, null, 2)}
            </pre>
          ) : (
            <p className="placeholder-text">
              Fill in the filters and click “Find tables”.
            </p>
          )}
        </section>
      </main>
    </div>
  );
}

export default App;
