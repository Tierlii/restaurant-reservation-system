import { useState } from "react";
import type { Preference, SearchRequest, Zone } from "../types/reservation";

interface FilterPanelProps {
  onSearch: (request: SearchRequest) => void;
  isLoading?: boolean;
}

const zoneOptions: { value: Zone; label: string }[] = [
  { value: "MAIN_HALL", label: "Main hall" },
  { value: "TERRACE", label: "Terrace" },
  { value: "PRIVATE_ROOM", label: "Private room" },
];

const preferenceOptions: { value: Preference; label: string }[] = [
  { value: "WINDOW", label: "Window" },
  { value: "QUIET", label: "Quiet / Privacy" },
  { value: "NEAR_KIDS_AREA", label: "Near kids area" },
  { value: "ACCESSIBLE", label: "Accessible" },
];

function getTodayDate(): string {
  return new Date().toISOString().split("T")[0];
}

function FilterPanel({ onSearch, isLoading = false }: FilterPanelProps) {
  const [date, setDate] = useState<string>(getTodayDate());
  const [time, setTime] = useState<string>("19:00");
  const [partySize, setPartySize] = useState<number>(4);
  const [zone, setZone] = useState<Zone | "">("");
  const [preferences, setPreferences] = useState<Preference[]>([]);

  const handlePreferenceChange = (preference: Preference) => {
    setPreferences((prev) =>
      prev.includes(preference)
        ? prev.filter((item) => item !== preference)
        : [...prev, preference]
    );
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const request: SearchRequest = {
      date,
      time,
      partySize,
      preferences,
      ...(zone ? { zone } : {}),
    };

    onSearch(request);
  };

  return (
    <div className="filter-panel">
      <h2 className="panel-title">Find a table</h2>

      <form onSubmit={handleSubmit} className="filter-form">
        <div className="form-group">
          <label htmlFor="date">Date</label>
          <input
            id="date"
            type="date"
            value={date}
            min={getTodayDate()}
            onChange={(e) => setDate(e.target.value)}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="time">Time</label>
          <input
            id="time"
            type="time"
            value={time}
            onChange={(e) => setTime(e.target.value)}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="partySize">Party size</label>
          <input
            id="partySize"
            type="number"
            min={1}
            max={20}
            value={partySize}
            onChange={(e) => setPartySize(Number(e.target.value))}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="zone">Zone</label>
          <select
            id="zone"
            value={zone}
            onChange={(e) => setZone(e.target.value as Zone | "")}
          >
            <option value="">Any zone</option>
            {zoneOptions.map((option) => (
              <option key={option.value} value={option.value}>
                {option.label}
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <span className="preferences-label">Preferences</span>

          <div className="preferences">
            {preferenceOptions.map((option) => (
              <label key={option.value} className="checkbox-row">
                <input
                  type="checkbox"
                  checked={preferences.includes(option.value)}
                  onChange={() => handlePreferenceChange(option.value)}
                />
                <span>{option.label}</span>
              </label>
            ))}
          </div>
        </div>

        <button type="submit" className="search-button" disabled={isLoading}>
          {isLoading ? "Searching..." : "Find tables"}
        </button>
      </form>
    </div>
  );
}

export default FilterPanel;
