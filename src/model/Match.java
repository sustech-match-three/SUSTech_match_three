package model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Match {
    private final Set<ChessboardPoint> points = new HashSet<>();

    public void addPoint(ChessboardPoint point) {
        points.add(point);
    }

    public int getSize() {
        return points.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return points.equals(match.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(points);
    }


}
