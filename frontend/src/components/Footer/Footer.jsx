import React from "react";

export default function Footer() {
  return (
    <footer
      style={{
        position: "fixed",
        left: 0,
        bottom: 0,
        width: "100%",
        backgroundColor: "rgba(63,81,181, 0.6)",
        color: "white",
        textAlign: "center",
        minHeight: 50,
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      Travel blog by Goran Yolovski
    </footer>
  );
}
