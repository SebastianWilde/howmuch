package com.example.sebastian.howmuch;

import android.icu.text.StringPrepParseException;
import android.os.Bundle;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static java.sql.DriverManager.println;

//    0 = visible
 //       4 = invisible
  //      8 = gone
public class MainActivity extends AppCompatActivity {
    EditText etpermaPorcentaje,etperma1,etperma1porcentaje,etperma2,etperma2porcentaje,etparcial,etparcialporcentaje,etparcialTeorico,etparcialPractico,
    etfinalTeorico,etfinalPractico,etparcialTeoricoPorcentaje,etparcialPracticoPorcentaje,etfinalTeoricoPorcentaje,etfinalPracticoPorcentaje,etfinalPorcentaje,
    etacumulado;

    double dbpermaPorcentaje,dbperma1,dbperma1porcentaje,dbperma2,dbperma2porcentaje,dbparcial,dbparcialporcentaje,dbfin,dbparcialTeorico,dbparcialPractico,
    dbfinalTeorico,dbfinalPractico,dbparcialTeoricoPorcentaje,dbparcialPracticoPorcentaje,dbfinalTeoricoPorcentaje,
    dbfinalPracticoPorcentaje,dbFinalPorcentaje,dbacumulado;

    Button btncalcular;

    TextView tvfin;

    LinearLayout Llayoutperma,LLayoutParcialPractico,LLayoutParcialTeorico,LLayoutFinalTeorico,LLayoutFinalPractico,LLayoutPerma1,
            LLayoutAcumulado,LLayoutParcial;

    CheckBox CBavanzado,CBconfigurarParcial,CBconfigurarFinal,CBacumulado;

    private boolean isEmpty(EditText myeditText) {
        return myeditText.getText().toString().trim().length() == 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CBavanzado = (CheckBox) findViewById(R.id.cbavanzado);
        CBconfigurarParcial = (CheckBox) findViewById(R.id.cbConfigurarParcial);
        CBconfigurarFinal = (CheckBox) findViewById(R.id.cbConfigurarfinal);
        CBacumulado = (CheckBox) findViewById(R.id.cbacumulado);

        Llayoutperma = (LinearLayout) findViewById(R.id.LLayoutPerma);
        LLayoutParcialTeorico = (LinearLayout) findViewById(R.id.LLayoutParcialTeorico);
        LLayoutParcialPractico = (LinearLayout) findViewById(R.id.LLayoutParcialPractico);
        LLayoutFinalTeorico = (LinearLayout) findViewById(R.id.LLayoutFinalTeorico);
        LLayoutFinalPractico = (LinearLayout) findViewById(R.id.LLayoutFinalPractico);
        LLayoutPerma1 = (LinearLayout) findViewById(R.id.LLayoutPerma1);
        LLayoutAcumulado = (LinearLayout) findViewById(R.id.LLayoutAcumulado);
        LLayoutParcial = (LinearLayout) findViewById(R.id.LLayoutParcial);

        etperma1 = (EditText) findViewById(R.id.perma1);
        etperma1porcentaje = (EditText) findViewById(R.id.perma1porcentaje);
        etperma2 = (EditText) findViewById(R.id.perma2);
        etperma2porcentaje = (EditText) findViewById(R.id.perma2porcentaje);
        etparcial = (EditText) findViewById(R.id.parcial);
        etparcialporcentaje = (EditText) findViewById(R.id.parcialporcentaje);
        etfinalPorcentaje = (EditText) findViewById(R.id.finalporcentaje);
        btncalcular = (Button) findViewById(R.id.calcular);
        tvfin = (TextView) findViewById(R.id.fin);

        btncalcular.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbfin = 0.0;
                dbperma2 = Double.parseDouble(etperma2.getText().toString());
                dbperma2porcentaje = Double.parseDouble(etperma2porcentaje.getText().toString());
                dbFinalPorcentaje = Double.parseDouble(etfinalPorcentaje.getText().toString());

                if (!CBacumulado.isChecked()) {
                    double dbNotaPermanete = 0.0;
                    double dbNotaParcial = 0.0;

                    dbperma1 = Double.parseDouble(etperma1.getText().toString());
                    dbperma1porcentaje = Double.parseDouble(etperma1porcentaje.getText().toString());

                    dbparcialporcentaje = Double.parseDouble(etparcialporcentaje.getText().toString());

                    if (CBavanzado.isChecked()) {
                        //Calculo de la permanente
                        dbpermaPorcentaje = Double.parseDouble(etpermaPorcentaje.getText().toString());
                        dbNotaPermanete = dbperma1 * (dbperma1porcentaje * (dbpermaPorcentaje / 100.0) / 100.0) +
                                dbperma2 * (dbperma2porcentaje * (dbpermaPorcentaje / 100.0) / 100.0);

                        //Calculo del parcial
                        dbparcialTeoricoPorcentaje = Double.parseDouble(etparcialTeoricoPorcentaje.getText().toString());
                        dbparcialPracticoPorcentaje = Double.parseDouble(etparcialPracticoPorcentaje.getText().toString());
                        dbparcialTeorico = Double.parseDouble(etparcialTeorico.getText().toString());
                        dbparcialPractico = Double.parseDouble(etparcialPractico.getText().toString());
                        dbparcial = dbparcialTeorico * dbparcialTeoricoPorcentaje / 100.0 +
                                dbparcialPractico * dbparcialPracticoPorcentaje / 100.0;
                    }
                    else {
                        //Calculo permanente
                        dbNotaPermanete = dbperma1 * dbperma1porcentaje / 100.0 +
                                dbperma2 * dbperma2porcentaje / 100.0;
                        //Calculo parcial
                        dbparcial = Double.parseDouble(etparcial.getText().toString());
                    }
                    System.out.println(dbNotaPermanete);
                    dbNotaParcial = dbparcial * dbparcialporcentaje / 100.0;
                    if (CBconfigurarParcial.isChecked())
                        etparcial.setText(Double.toString(dbparcial));
                    System.out.println(dbNotaParcial);
                    dbfin = 11.5 - dbNotaParcial - dbNotaPermanete;
                }
                //Calculo de la nota final
                else{
                    dbperma2 = Double.parseDouble(etperma2.getText().toString());
                    dbperma2porcentaje = Double.parseDouble(etperma2porcentaje.getText().toString());
                    dbFinalPorcentaje = Double.parseDouble(etfinalPorcentaje.getText().toString());
                    dbacumulado = Double.parseDouble(etacumulado.getText().toString());
                    if(CBavanzado.isChecked()){
                        dbpermaPorcentaje = Double.parseDouble(etpermaPorcentaje.getText().toString());
                        dbfin = 11.5 - dbacumulado - dbperma2*(dbperma2porcentaje*(dbpermaPorcentaje/100.0)/100.0);
                    }
                    else
                        dbfin = 11.5 - dbacumulado - dbperma2*(dbperma2porcentaje/100.0);
                }

                System.out.println(dbfin);
                dbfin = dbfin/(dbFinalPorcentaje/100.0);
                if (CBconfigurarFinal.isChecked()){
                    double notaFinalTemporal = 0.0;
                    dbfinalPracticoPorcentaje = Double.parseDouble(etfinalPracticoPorcentaje.getText().toString());
                    dbfinalTeoricoPorcentaje = Double.parseDouble(etfinalTeoricoPorcentaje.getText().toString());
                    if(isEmpty(etfinalTeorico)){
                        dbfinalPractico = Double.parseDouble(etfinalPractico.getText().toString());
                        notaFinalTemporal = dbfinalPractico * dbfinalPracticoPorcentaje /100.0;
                        dbfin -= notaFinalTemporal;
                        dbfin = dbfin / (dbfinalTeoricoPorcentaje/100.0);
                    }
                    if (isEmpty(etfinalPractico)){
                        dbfinalTeorico = Double.parseDouble(etfinalTeorico.getText().toString());
                        notaFinalTemporal = dbfinalTeorico * dbfinalTeoricoPorcentaje /100.0;
                        dbfin -= notaFinalTemporal;
                        dbfin = dbfin / (dbfinalPracticoPorcentaje/100.0);
                    }
                }

                dbfin = Math.round(dbfin*10)/10.0 + 0.1;
                System.out.println("Nota final");
                System.out.println(dbfin);
                String Mensaje;
                if(dbfin>20)
                    Mensaje = "Lo siento, es imposible que apruebes";
                else if (dbfin<0)
                    Mensaje = "GG Izzy, ya aprobaste";
                else
                    Mensaje = Double.toString(dbfin);
                tvfin.setText(Mensaje);
            }
        });
    }
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.cbavanzado:
                if (checked){
                    Llayoutperma.setVisibility(0);
                    if (!CBacumulado.isChecked())
                        CBconfigurarParcial.setVisibility(View.VISIBLE);

                    CBconfigurarFinal.setVisibility(View.VISIBLE);
                    etpermaPorcentaje = (EditText) findViewById(R.id.permaporcentaje);
                    etpermaPorcentaje.setText("40");
                    dbpermaPorcentaje = Double.parseDouble(etpermaPorcentaje.getText().toString());
                    etperma1porcentaje.setText("50");
                    etperma2porcentaje.setText("50");
                }
                // Put some meat on the sandwich
                else{
                    Llayoutperma.setVisibility(8);
                    CBconfigurarParcial.setChecked(false);
                    CBconfigurarFinal.setChecked(false);
                    CBconfigurarParcial.setVisibility(View.GONE);
                    CBconfigurarFinal.setVisibility(View.GONE);
                    etperma1porcentaje.setText("20");
                    etperma2porcentaje.setText("20");

                    LLayoutParcialTeorico.setVisibility(View.GONE);
                    LLayoutParcialPractico.setVisibility(8);
                    etparcialporcentaje.setText("30");
                    LLayoutFinalTeorico.setVisibility(View.GONE);
                    LLayoutFinalPractico.setVisibility(8);
                    etfinalPorcentaje.setText("30");
                }
                // Remove the meat
                break;
            case R.id.cbConfigurarParcial:
                if (checked){
                    LLayoutParcialTeorico.setVisibility(View.VISIBLE);
                    LLayoutParcialPractico.setVisibility(0);
                    etparcialporcentaje.setText("30");
                    etparcialTeorico = (EditText)findViewById(R.id.parcialTeorico);
                    etparcialPractico = (EditText)findViewById(R.id.parcialPractico);
                    etparcialTeoricoPorcentaje = (EditText) findViewById(R.id.parcialTeoricoPorcentaje);
                    etparcialPracticoPorcentaje = (EditText) findViewById(R.id.parcialPracticoPorcentaje);

                    etparcialTeoricoPorcentaje.setText("50");
                    etparcialPracticoPorcentaje.setText("50");
                }
                // Cheese me
                else{
                    LLayoutParcialTeorico.setVisibility(View.GONE);
                    LLayoutParcialPractico.setVisibility(8);
                    etparcialporcentaje.setText("30");
                }
                // I'm lactose intolerant
                break;
            case R.id.cbConfigurarfinal:
                if (checked){
                    LLayoutFinalTeorico.setVisibility(View.VISIBLE);
                    LLayoutFinalPractico.setVisibility(0);
                    etfinalPorcentaje.setText("30");
                    etfinalTeorico = (EditText)findViewById(R.id.finalTeorico);
                    etfinalPractico = (EditText)findViewById(R.id.finalPractico);
                    etfinalTeoricoPorcentaje = (EditText) findViewById(R.id.finalTeoricoPorcentaje);
                    etfinalPracticoPorcentaje= (EditText) findViewById(R.id.finalPracticoPorcentaje);
                    etfinalTeoricoPorcentaje.setText("50");
                    etfinalPracticoPorcentaje.setText("50");
                }
                // Cheese me
            else{
                    LLayoutFinalTeorico.setVisibility(View.GONE);
                    LLayoutFinalPractico.setVisibility(8);
                    etfinalPorcentaje.setText("30");
                }
                // I'm lactose intolerant
                break;

            case R.id.cbacumulado:
                if(checked){
                    LLayoutAcumulado.setVisibility(View.VISIBLE);
                    LLayoutPerma1.setVisibility(View.GONE);
                    LLayoutParcial.setVisibility(View.GONE);
                    etacumulado = (EditText) findViewById(R.id.acumulado);
                    if(CBconfigurarParcial.isChecked()){
                        LLayoutParcialTeorico.setVisibility(View.GONE);
                        LLayoutParcialPractico.setVisibility(8);
                    }
                }
                else{
                    LLayoutAcumulado.setVisibility(View.GONE);
                    LLayoutPerma1.setVisibility(View.VISIBLE);
                    LLayoutParcial.setVisibility(View.VISIBLE);
                    if(CBconfigurarParcial.isChecked()) {
                        LLayoutParcialTeorico.setVisibility(View.VISIBLE);
                        LLayoutParcialPractico.setVisibility(0);
                    }
                    if (CBavanzado.isChecked()){
                        CBconfigurarParcial.setVisibility(View.VISIBLE);
                        CBconfigurarFinal.setVisibility(View.VISIBLE);
                    }
                }
        }
    }
}
