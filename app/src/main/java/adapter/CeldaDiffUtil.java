package adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import celda.Celda;

import java.util.Objects;

public class CeldaDiffUtil extends DiffUtil.ItemCallback<Celda> {

    @Override
    public boolean areItemsTheSame(
            @NonNull Celda celda1, @NonNull Celda celda2) {
        return celda1.getPosicion() == celda2.getPosicion();
    }

    @Override
    public boolean areContentsTheSame(
            @NonNull Celda celda1, @NonNull Celda celda2) {
        return Objects.equals(celda1.getFila(), celda2.getFila()) && Objects.equals(celda1.getColumna(), celda2.getColumna());
    }
}
