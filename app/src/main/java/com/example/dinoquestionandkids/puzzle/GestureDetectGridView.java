package com.example.dinoquestionandkids.puzzle;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;

public class GestureDetectGridView extends GridView {
    private GestureDetector miGestureDetector;
    private boolean movimientoConfirmado = false;
    private float pulsarX;
    private float pulsarY;

    private static final int DISTANCIA_MINIMA_PULSACION = 100;
    private static final int DISTANCIA_MAXIMA_PULSACION = 100;
    private static final int LIMITE_VELOCIDAD_PULSACION = 100;

    public GestureDetectGridView(Context context) {
        super(context);
        inicio(context);
    }

    public GestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inicio(context);
    }

    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inicio(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) // API 21
    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr,
                                 int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inicio(context);
    }

    private void inicio(final Context context) {
        miGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocidadX, float velocidadY) {
                final int posicion = GestureDetectGridView.this.pointToPosition
                        (Math.round(e1.getX()), Math.round(e1.getY()));
                if (Math.abs(e1.getY() - e2.getY()) > DISTANCIA_MAXIMA_PULSACION) {
                    if (Math.abs(e1.getX() - e2.getX()) > DISTANCIA_MAXIMA_PULSACION
                            || Math.abs(velocidadY) < LIMITE_VELOCIDAD_PULSACION) {
                        return false;
                    }
                    if (e1.getY() - e2.getY() > DISTANCIA_MINIMA_PULSACION) {
                        Puzzle2Activity.movimientoPiezas(context, Puzzle2Activity.arriba, posicion);
                    } else if (e2.getY() - e1.getY() > DISTANCIA_MINIMA_PULSACION) {
                        Puzzle2Activity.movimientoPiezas(context, Puzzle2Activity.abajo, posicion);
                    }
                } else {
                    if (Math.abs(velocidadX) < LIMITE_VELOCIDAD_PULSACION) {
                        return false;
                    }
                    if (e1.getX() - e2.getX() > DISTANCIA_MINIMA_PULSACION) {
                        Puzzle2Activity.movimientoPiezas(context, Puzzle2Activity.izquierda, posicion);
                    } else if (e2.getX() - e1.getX() > DISTANCIA_MINIMA_PULSACION) {
                        Puzzle2Activity.movimientoPiezas(context, Puzzle2Activity.derecha, posicion);
                    }
                }
                return super.onFling(e1, e2, velocidadX, velocidadY);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int accion = ev.getActionMasked();
        miGestureDetector.onTouchEvent(ev);

        if (accion == MotionEvent.ACTION_CANCEL || accion == MotionEvent.ACTION_UP) {
            movimientoConfirmado = false;
        } else if (accion == MotionEvent.ACTION_DOWN) {
            pulsarX = ev.getX();
            pulsarY = ev.getY();
        } else {
            if (movimientoConfirmado) {
                return true;
            }
            float dX = (Math.abs(ev.getX() - pulsarX));
            float dY = (Math.abs(ev.getY() - pulsarY));
            if ((dX > DISTANCIA_MINIMA_PULSACION) || (dY > DISTANCIA_MINIMA_PULSACION)) {
                movimientoConfirmado = true;
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return miGestureDetector.onTouchEvent(ev);
    }
}
