const generateCSVContent = (headers, data) => {
  if (!Array.isArray(headers) || headers.length === 0) {
    throw new Error("Headers must be a non-empty array.");
  }
  if (!Array.isArray(data)) {
    throw new Error("Data must be an array.");
  }

  const csvContent =
    headers.join(",") +
    "\r\n" +
    data
      .map(e => headers.map(h => e[h] || "").join(","))
      .join("\r\n");

  return csvContent;
};

const downloadCSVChart = (headers, data, fileName) => {
  try {
    const csvContent = generateCSVContent(headers, data);
    const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
    const link = document.createElement("a");
    const url = URL.createObjectURL(blob);
    link.setAttribute("href", url);
    link.setAttribute("download", fileName);
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    URL.revokeObjectURL(url);
  } catch (error) {
    console.error("Error generating CSV:", error);
  }
};

export { downloadCSVChart };
