import type { TableAvailability } from "../types/reservation";

interface TableCardProps {
  table: TableAvailability;
  selectedTableId?: number | null;
  onSelect?: (table: TableAvailability) => void;
}

function TableCard({ table, selectedTableId, onSelect }: TableCardProps) {
  const isSelected = selectedTableId === table.id;

  let statusClass = "table-card occupied";
  if (table.recommended) {
    statusClass = "table-card recommended";
  } else if (table.available) {
    statusClass = "table-card available";
  }

  if (isSelected) {
    statusClass += " selected";
  }

  return (
    <button
      type="button"
      className={statusClass}
      style={{ left: `${table.x}px`, top: `${table.y}px` }}
      onClick={() => onSelect?.(table)}
      disabled={!table.available && !table.recommended}
      title={`${table.name} • Capacity: ${table.capacity} • Zone: ${table.zone}`}
    >
      <span className="table-name">{table.name}</span>
      <span className="table-capacity">{table.capacity} seats</span>
    </button>
  );
}

export default TableCard;
