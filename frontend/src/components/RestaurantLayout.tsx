import type { TableAvailability } from "../types/reservation";
import TableCard from "./TableCard";

interface RestaurantLayoutProps {
  tables: TableAvailability[];
  selectedTableId?: number | null;
  onSelectTable?: (table: TableAvailability) => void;
}

function RestaurantLayout({
  tables,
  selectedTableId,
  onSelectTable,
}: RestaurantLayoutProps) {

  if (!tables || tables.length === 0) {
    return (
      <section className="layout-panel">
        <h2 className="panel-title">Restaurant layout</h2>

        <p className="placeholder-text">
          Use the search filters to find available tables.
        </p>
      </section>
    );
  }  

    return (
    <section className="layout-panel">
      <div className="layout-header">
        <div>
          <h2 className="panel-title">Restaurant layout</h2>
          <p className="layout-subtitle">
            Available, occupied and recommended tables are shown on the floor
            plan.
          </p>
        </div>

        <div className="legend">
          <div className="legend-item">
            <span className="legend-dot available-dot" />
            <span>Available</span>
          </div>
          <div className="legend-item">
            <span className="legend-dot occupied-dot" />
            <span>Occupied</span>
          </div>
          <div className="legend-item">
            <span className="legend-dot recommended-dot" />
            <span>Recommended</span>
          </div>
        </div>
      </div>

      <div className="restaurant-map">
          <div className="zone-label zone-main-top-left">Main Hall</div>
          <div className="zone-label zone-main-top-right">Main Hall</div>

          <div className="zone-label zone-terrace">Terrace</div>
          <div className="zone-label zone-private">Private Room</div>

          <div className="zone-label zone-main-bottom">Main Hall</div>
        {tables.map((table) => (
          <TableCard
            key={table.id}
            table={table}
            selectedTableId={selectedTableId}
            onSelect={onSelectTable}
          />
        ))}
      </div>
    </section>
  );
}

export default RestaurantLayout;
