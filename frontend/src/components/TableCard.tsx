import type { TableAvailability } from "../types/reservation";

interface TableCardProps {
  table: TableAvailability;
  selectedTableId?: number | null;
  onSelect?: (table: TableAvailability) => void;
}

function TableCard({ table, selectedTableId, onSelect }: TableCardProps) {

  const statusClass = table.recommended
    ? "table-recommended"
    : table.available
    ? "table-available"
    : "table-occupied";

  const selectedClass =
    selectedTableId === table.id ? "table-selected" : "";

  const handleClick = () => {
    if (!table.available && !table.recommended) {
      return;
    }

    onSelect?.(table);
  };

  return (
    <div
      className={`table-card ${statusClass} ${selectedClass}`}
      style={{
        left: table.x,
        top: table.y,
      }}
      onClick={handleClick}
      title={`${table.name} • capacity ${table.capacity}`}
    >
      {table.name}
    </div>
  );
}

export default TableCard;
