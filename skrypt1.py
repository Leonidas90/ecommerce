import matplotlib.pyplot as plt
from matplotlib.backends.backend_agg import FigureCanvasAgg
from reportlab.pdfgen import canvas
import tkinter as tk
from tkinter import filedialog
import json
import matplotlib as mpl
from datetime import date
import pdoc

mpl.rcParams['font.family'] = 'georgia'

# Open the JSON file for reading
with open('dane.json', 'r') as file:
    # Load the JSON data into a Python dictionary
    sales_data = json.load(file)

#lista produktów
products = set(sale["product_name"] for sale in sales_data)

#lista dni tygodnia
lista_dni_tygodnia = ['Pon', 'Wt', 'Śr', 'Czw', 'Pt', 'So', 'Nd']


#wartość sprzedaży dla konkretnego produktu
def get_product_value(sales_data, product_name):
    product_value = 0
    for sale in sales_data:
        if sale["product_name"] == product_name:
            product_value += sale["value"]
    return product_value

sprzedaz_ogolem = sum(sale["value"] for sale in sales_data)


#wartość sprzedaży dla konkretnego dnia
def get_daily_product_value(sales_data, dzien_tygodnia):
    product_value = 0
    for dzien in sales_data:
        d = date.fromisoformat(dzien['date'])
        weekday = d.weekday()
        weekday_name = ['Pon', 'Wt', 'Śr', 'Czw', 'Pt', 'So', 'Nd'][weekday]
    
        if weekday_name == dzien_tygodnia:
            product_value += dzien["value"]
    return product_value

#wartość sprzedaży dla konkretnego wieku
def get_product_value_for_age(sales_data, wiek_min, wiek_maks):
    product_value = 0
    for wiek in sales_data:
        if wiek['customer_age'] >= wiek_min and wiek['customer_age'] < wiek_maks:
            product_value += wiek["value"]
    return product_value


#Utworzenie dict z listami produktów i odpowiadającej im sprzedaży 
sprzedaz_produktow = {'Nazwa_Produktu':[],
                      'Wartosc_sprzedazy':[]}
for produkt in products:
    
    product_value = get_product_value(sales_data, produkt)
    sprzedaz_produktow['Nazwa_Produktu'].append(produkt)
    sprzedaz_produktow['Wartosc_sprzedazy'].append(product_value)
    

#Utworzenie dict z listami dniami tygodnia i odpowiadającej im sprzedaży 
sprzedaz_produktow_tygodniowa = {'Dzien_tygodnia':[],
                                 'Wartosc_sprzedazy':[]}
for dzien in lista_dni_tygodnia:
    
    product_value = get_daily_product_value(sales_data, dzien)
    sprzedaz_produktow_tygodniowa['Dzien_tygodnia'].append(dzien)
    sprzedaz_produktow_tygodniowa['Wartosc_sprzedazy'].append(product_value)
    #print(f"Total value for {dzien}: {product_value}")

#Utworzenie dict z listami wieku i odpowiadającej im zakupom 
sprzedaz_wg_wieku= {'Przedzial_wieku':[],
                    'Wartosc_sprzedazy':[]}

product_value = get_product_value_for_age(sales_data, 0, 18)
sprzedaz_wg_wieku['Przedzial_wieku'].append('18 lat i młodsi')
sprzedaz_wg_wieku['Wartosc_sprzedazy'].append(product_value)
product_value = get_product_value_for_age(sales_data, 18, 35)
sprzedaz_wg_wieku['Przedzial_wieku'].append('18 lat - 35 lat')
sprzedaz_wg_wieku['Wartosc_sprzedazy'].append(product_value)
product_value = get_product_value_for_age(sales_data, 35, 55)
sprzedaz_wg_wieku['Przedzial_wieku'].append('35 lat - 55 lat')
sprzedaz_wg_wieku['Wartosc_sprzedazy'].append(product_value)
product_value = get_product_value_for_age(sales_data, 55, 100)
sprzedaz_wg_wieku['Przedzial_wieku'].append('35 lat i starsi')
sprzedaz_wg_wieku['Wartosc_sprzedazy'].append(product_value)



#Pie chart
sorted_list_sp_pr = sorted(zip(sprzedaz_produktow['Nazwa_Produktu'], sprzedaz_produktow['Wartosc_sprzedazy']), key=lambda x: x[1], reverse=True)
sorted_keys, sorted_values = zip(*sorted_list_sp_pr)
sorted_sprzedaz_produktow = {'Nazwa_Produktu': list(sorted_keys), 'Wartosc_sprzedazy': list(sorted_values)}
labels = sorted_sprzedaz_produktow['Nazwa_Produktu']
values = sorted_sprzedaz_produktow['Wartosc_sprzedazy']

# Plot
fig, ax = plt.subplots()
if len(labels) > 9:
    labels = labels[:9] + ['Inne produkty']
    values = values[:9] + [sum(values[9:])]
ax.pie(values, labels=labels, autopct='%1.1f%%', counterclock = False, shadow=True)
ax.axis('equal')
plt.title('Najlepiej sprzedające się produkty')
fig.savefig('best_produkty.png')

#Bar chart
labels = ['Pon', 'Wt', 'Śr', 'Czw', 'Pt', 'So', 'Nd']
values = sprzedaz_produktow_tygodniowa['Wartosc_sprzedazy']
#plt.bar(labels, values)
fig, ax = plt.subplots()
ax.bar(labels, values)
ax.set_ylim(bottom=30000)
# Add labels and title
plt.xlabel('Dni tygodnia')
plt.ylabel('Wartość sprzedaży w PLN')
plt.title('Wartość sprzedaży w poszczególnych dniach tygodnia')
fig.savefig('produkty_dni_tyg.png')

# Wykres sprzedaży
sorted_list_sp_pr = sorted(zip(sprzedaz_produktow['Nazwa_Produktu'], sprzedaz_produktow['Wartosc_sprzedazy']), key=lambda x: x[1], reverse=True)
sorted_keys, sorted_values = zip(*sorted_list_sp_pr)
sorted_sprzedaz_produktow = {'Nazwa_Produktu': list(sorted_keys), 'Wartosc_sprzedazy': list(sorted_values)}
labels = sorted_sprzedaz_produktow['Nazwa_Produktu']
values = sorted_sprzedaz_produktow['Wartosc_sprzedazy']

#Pie chart wiek
labels = sprzedaz_wg_wieku['Przedzial_wieku']
values = sprzedaz_wg_wieku['Wartosc_sprzedazy']
# Plot
fig, ax = plt.subplots()
explode_val = (0, 0.4, 0, 0.1)
ax.pie(values, labels=labels, autopct='%1.1f%%', explode = explode_val, shadow=False)
ax.axis('equal')
plt.title('Podział klientów wg wieku')
fig.savefig('wiek_produkty.png')

# Display a file dialog to choose the location to save the PDF
root = tk.Tk()
root.withdraw()
pdf_file = filedialog.asksaveasfilename(defaultextension='.pdf', 
                                        initialfile='raport_sprzedazy.pdf')

# Create the PDF document
c = canvas.Canvas(pdf_file)

# Add the chart to the PDF
c.drawImage('produkty_dni_tyg.png', 100, 50, 500, 370)


# Add text to the PDF
c.setFont("Times-Roman", 16)
c.drawString(200, 790, 'Raport sprzedazy sklepu internetowego')
c.setFont("Times-Roman", 12)
c.drawString(50, 750, 'To jest podstawowy raport na potrzeby aplikacji zaliczeniowej.')
c.drawString(50, 725, 'Projekt byl przygotowywany w ramach zaliczenia przedmiotow:')
c.setFont("Times-Roman", 14)
c.drawString(50, 690, 'Praktyka Programowania Python')
c.drawString(50, 660, 'Projektowanie Aplikacji Internetowych - PHP, Python, Javascript')
c.drawString(50, 630, 'Organizacja i rozwój projektów Open Source')
c.setFont("Times-Roman", 12)
c.drawString(50, 600, 'Jest to skrypt napsany w Pythonie')
c.drawString(50, 575, 'W raporcie za pomoca matplotlib generowane sa trzy wykresy')
c.drawString(50, 550, 'Wynik jest zapisywany do pliku pdf uzywajac metod biblioteki reportlab')
c.drawString(50, 525, 'Dane do generacji wykresów zostaly utworzone w pliku JSON')
c.setFont("Times-Roman", 14)
c.drawString(50, 450, 'Calkowita sprzedaz w biezacym tygodniu wyniosla: ' + str(sprzedaz_ogolem) + ' PLN')

#Kolejna strona pdf
c.showPage()
c.drawImage('best_produkty.png', 50, 50, 500, 370)
c.drawImage('wiek_produkty.png', 50, 420, 500, 370)
# Save the PDF
c.save()
pdoc --html