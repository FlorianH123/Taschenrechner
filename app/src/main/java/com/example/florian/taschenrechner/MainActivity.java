package com.example.florian.taschenrechner;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void updateDisplayOnClick(View view) {
        switch (view.getId()) {
            case R.id.button_division:
                updateDisplayString("/");
                break;

            case R.id.button_multiplication:
                updateDisplayString("*");
                break;

            case R.id.button_minus:
                updateDisplayString("-");
                break;

            case R.id.button_plus:
                updateDisplayString("+");
                break;

            case R.id.button_zero:
                updateDisplayString("0");
                break;

            case R.id.button_one:
                updateDisplayString("1");
                break;

            case R.id.button_two:
                updateDisplayString("2");
                break;
            case R.id.button_three:
                updateDisplayString("3");
                break;

            case R.id.button_four:
                updateDisplayString("4");
                break;

            case R.id.button_five:
                updateDisplayString("5");
                break;

            case R.id.button_six:
                updateDisplayString("6");
                break;

            case R.id.button_seven:
                updateDisplayString("7");
                break;

            case R.id.button_eight:
                updateDisplayString("8");
                break;

            case R.id.button_nine:
                updateDisplayString("9");
                break;

            case R.id.button_point:
                updateDisplayString(".");
                break;

            case R.id.button_clearOneChar:
                deleteLastCharacter();
                break;

            case R.id.button_opening_bracket:
                updateDisplayString("(");
                break;

            case R.id.button_closing_bracket:
                updateDisplayString(")");
                break;

            case R.id.button_clear_all:
                clearAllCharacter();
                break;

            case R.id.button_cosinus:
                updateDisplayString("cos(");
                break;

            case R.id.button_sinus:
                updateDisplayString("sin(");
                break;

            case R.id.button_tangens:
                updateDisplayString("tan(");
                break;

            case R.id.button_square_root:
                updateDisplayString("sqrt(");
                break;
        }
    }

    private void updateDisplayString(String operation) {
        TextView display = findViewById(R.id.displayView);
        String newDisplayString = display.getText().toString();

        if (newDisplayString.equals("0")) {
            if (operation.equals(".") || operation.equals("/") || operation.equals("*") || operation.equals("+") || operation.equals("-")) {
                newDisplayString += operation;
            } else {
                newDisplayString = operation;
            }
        } else {
            if (newDisplayString.startsWith("=")) {
                if (operation.equals("/") || operation.equals("*") || operation.equals("+") || operation.equals("-")) {
                    newDisplayString = newDisplayString.substring(1) + operation;
                } else if (operation.equals(".")) {
                    newDisplayString = "0" + operation;
                } else {
                    newDisplayString = String.valueOf(operation);
                }

                display.setTextColor(Color.BLACK);
            } else {
                newDisplayString += operation;
            }
        }

        display.setText(newDisplayString);
    }

    private void clearAllCharacter() {
        TextView display = findViewById(R.id.displayView);
        display.setText("0");
        display.setTextColor(Color.BLACK);
    }

    private void deleteLastCharacter() {
        int stringLength;
        TextView display = findViewById(R.id.displayView);
        String newDisplayString = display.getText().toString();

        if (!newDisplayString.equals("0")) {
            stringLength = newDisplayString.length();

            if (stringLength > 1 && !newDisplayString.startsWith("=")) {
                if (newDisplayString.endsWith("cos(")) {
                    newDisplayString = newDisplayString.substring(0, newDisplayString.length() - 4);
                } else if (newDisplayString.endsWith("sin(")) {
                    newDisplayString = newDisplayString.substring(0, newDisplayString.length() - 4);
                } else if (newDisplayString.endsWith("tan(")) {
                    newDisplayString = newDisplayString.substring(0, newDisplayString.length() - 4);
                } else if ( newDisplayString.endsWith("sqrt(")) {
                    newDisplayString = newDisplayString.substring(0, newDisplayString.length() - 5);
                } else{
                    newDisplayString = newDisplayString.substring(0, stringLength - 1);
                }

                if (newDisplayString.length() == 0) {
                    newDisplayString = "0";
                }

                display.setText(newDisplayString);
            } else {
                display.setText("0");
                display.setTextColor(Color.BLACK);
            }
        }
    }

    public void evaluateExpression(View view) {
        String resultString;
        TextView display = findViewById(R.id.displayView);
        String expression = display.getText().toString();

        try {
            Expression e = new ExpressionBuilder(expression).build();
            double result = e.evaluate();
            resultString = String.valueOf(result);

            if (resultString.endsWith(".0")) {
                display.setText("=" + resultString.substring(0, resultString.length() - 2));
            } else {
                display.setText("=" + resultString);
            }

            display.setTextColor(Color.RED);
        } catch (RuntimeException ex) {
            Toast.makeText(MainActivity.this, R.string.expression_error , Toast.LENGTH_SHORT).show();
            display.setText("0");
        }
    }

    public void showOperationMenu(View view) {
        PopupMenu popup = new PopupMenu(MainActivity.this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.operation_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.menu_item_cosinus:
                        updateDisplayString("cos(");
                        break;

                    case R.id.menu_item_sinus:
                        updateDisplayString("sin(");
                        break;

                    case R.id.menu_item_tangens:
                        updateDisplayString("tan(");
                        break;

                    case R.id.menu_item_square_root:
                        updateDisplayString("sqrt(");
                        break;
                }
                return true;
            }
        });

        popup.show();
    }

    protected void onRestoreInstanceState(Bundle saveInstanteState) {

    }

    protected void onSaveInstanceState(Bundle outState) {

    }
}
