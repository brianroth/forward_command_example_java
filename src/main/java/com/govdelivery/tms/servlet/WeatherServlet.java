package com.govdelivery.tms.servlet;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.govdelivery.tms.model.Weather;

@SuppressWarnings("serial")
@WebServlet(name = "WeatherServlet", urlPatterns = { "/weather" })
public class WeatherServlet extends HttpServlet {

    private DefaultHttpClient httpClient;

    public WeatherServlet() {
        this.httpClient = new DefaultHttpClient();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletOutputStream out = resp.getOutputStream();
        StringBuilder response = new StringBuilder("Current Java Weather: ");

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            Weather weather = mapper.readValue(doQuery(req.getParameter("sms_body")), Weather.class);

            if (null != weather && null != weather.getWeatherStatistics()) {
                response.append(String.format("%.0f degrees", weather.getWeatherStatistics().getTemp()));

                if (null != weather.getWeatherDescriptions() && weather.getWeatherDescriptions().length > 0) {
                    response.append(String.format(" and %s", weather.getWeatherDescriptions()[0].getDetails()));
                }
            } else {
                response.append("unknown");
            }
        } catch (JsonParseException | URISyntaxException e) {
            response.append("unknown");
        }

        out.write(response.toString().getBytes());
        out.flush();
        out.close();
    }

    private String doQuery(String cityName) throws IOException, URISyntaxException {
        String responseBody = null;

        URIBuilder builder = new URIBuilder("http://api.openweathermap.org/data/2.5/weather");
        builder.addParameter("q", cityName);
        builder.addParameter("type", "json");
        builder.addParameter("units", "imperial");

        HttpGet httpGet = new HttpGet(builder.build());

        HttpResponse response = this.httpClient.execute(httpGet);

        try {
            final HttpEntity responseEntity = response.getEntity();

            responseBody = EntityUtils.toString(response.getEntity());

            EntityUtils.consumeQuietly(responseEntity);
        } catch (IOException e) {
            throw e;
        } catch (RuntimeException re) {
            httpGet.abort();
            throw re;
        }

        return responseBody;
    }
}
