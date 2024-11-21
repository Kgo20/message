const dates = ['2024-01-01', '2024-02-01', '2024-03-01', '2024-04-01', '2024-05-01', '2024-06-01', '2024-07-01'];
const invested = [20000, 25000, 30000, 30000, 35000, 35000, 35000];
const portfolioValue = [20000, 26500, 26000, 31000, 35000, 34000, 32000];
let colore = "red";
let fillColore = 'rgba(255, 0, 0, 0.5)'

if (portfolioValue[6] > invested[6]) {
    colore = "green";
    fillColore = 'rgba(0, 128, 0, 0.5)';
}
const portfolioTrace = {
    x: dates,
    y: portfolioValue,
    mode: 'lines',
    fill: 'tozeroy',
    fillcolor: fillColore,
    line: {color: colore},
    hoverinfo: 'y'
};
const investedTrace = {
    x: dates,
    y: invested,
    mode: 'lines',
    line: {color: 'gray', dash: 'dot'},
    name: 'Invested Amount'
};
const data = [portfolioTrace, investedTrace];
const layout = {
    showlegend: false,
};
const config = {
    responsive: true,
    displayModeBar: false,
};
const graphDiv = document.getElementById('portfolio-graph');
Plotly.newPlot(graphDiv, data, layout, config);
const resizeObserver = new ResizeObserver(() => {
    Plotly.Plots.resize(graphDiv);
});
resizeObserver.observe(graphDiv);
