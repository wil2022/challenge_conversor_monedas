import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultarTasaCambio {

    public void tasasDeCambio(CurrencyCodes baseCurrency, CurrencyCodes targetCurrency, double valorAConvertir){

        String apiKey = "1d7943378f95697beeec2a35";
        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/"+apiKey+"/latest/"+baseCurrency);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = new Gson().fromJson(response.body(), JsonObject.class);

            JsonObject tasasConversion = jsonObject.getAsJsonObject("conversion_rates");
            var valorMoneda = tasasConversion.get(String.valueOf(targetCurrency)).getAsDouble();
            var conversion = conversionMoneda(valorMoneda, valorAConvertir);

            System.out.println("el valor " + valorAConvertir +" "+ baseCurrency + " corresponde al valor final de => " +
                    conversion+ " "+targetCurrency);
        } catch (Exception e) {
            throw new RuntimeException("Codigo de moneda no encontrado.");
        }

    }

    public double conversionMoneda(double moneda, double valor){

        return moneda * valor;
    }

}
