document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("formCadastro");
  if (!form) return; 
function formatarData(dataISO) {
  if (!dataISO) return "";
  const [ano, mes, dia] = dataISO.split("-");
  return `${dia}/${mes}/${ano}`;
function formatarData(dataISO) {
  if (!dataISO) return "";
  const [ano, mes, dia] = dataISO.split("-");
  return `${dia}/${mes}/${ano}`;
}

function formatarHora(horaISO) {
  if (!horaISO) return "";
  return horaISO.substring(0, 5);
}

  
}

function formatarHora(horaISO) {
  if (!horaISO) return "";
  return horaISO.substring(0, 5); 
}


  const tipo = form.dataset.tipo || "";
  const mensagemDiv = document.getElementById("mensagem");
  const API = "http://localhost:8080";

  function mostrarMensagem(msg, tipoMsg = "sucesso") {
    if (!mensagemDiv) return;
    mensagemDiv.textContent = msg;
    mensagemDiv.style.color = tipoMsg === "erro" ? "red" : "green";
  }

const cpfInput = document.getElementById("cpf");
if (cpfInput) {
  cpfInput.addEventListener("input", function (e) {
    let value = e.target.value.replace(/\D/g, ""); 
    value = value.replace(/(\d{3})(\d)/, "$1.$2");
    value = value.replace(/(\d{3})(\d)/, "$1.$2");
    value = value.replace(/(\d{3})(\d{1,2})$/, "$1-$2");
    e.target.value = value;
  });
}

const horaInput = document.getElementById("horaAgendamento");
if (horaInput && horaInput.type === "text") {
  horaInput.addEventListener("input", function (e) {
    let value = e.target.value.replace(/\D/g, "");
    if (value.length >= 3) {
      value = value.replace(/(\d{2})(\d)/, "$1:$2");
    }
    e.target.value = value;
  });
}

const dataInput = document.getElementById("dataAgendamento");
if (dataInput && dataInput.type === "text") {
  dataInput.addEventListener("input", function (e) {
    let value = e.target.value.replace(/\D/g, "");
    if (value.length >= 5) {
      value = value.replace(/(\d{2})(\d{2})(\d{0,4})/, "$1/$2/$3");
    } else if (value.length >= 3) {
      value = value.replace(/(\d{2})(\d)/, "$1/$2");
    }
    e.target.value = value;
  });
}


  //Usuário
 
if (tipo === "usuario") {
  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const id = document.getElementById("id").value;
    const payload = {
      nome: document.getElementById("nome").value,
      email: document.getElementById("email").value,
      senha: document.getElementById("senha").value,
      tipo: document.getElementById("tipo").value
    };

    try {
      const url = id ? `${API}/usuarios/${id}` : `${API}/usuarios`;
      const method = id ? "PUT" : "POST";

      const res = await fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });

      if (!res.ok) {
        mostrarMensagem("❌ Erro ao salvar usuário.", "erro");
        return;
      }

      mostrarMensagem(id ? "✏️ Usuário atualizado!" : "✅ Usuário cadastrado!");
      form.reset();
      document.getElementById("id").value = ""; 
      await carregarUsuarios(); 
    } catch (err) {
      console.error(err);
      mostrarMensagem("⚠ Falha de conexão com o servidor.", "erro");
    }
  });

  // Listagem dos usuários
  async function carregarUsuarios() {
    try {
      const res = await fetch(`${API}/usuarios`);
      if (!res.ok) throw new Error("Erro ao buscar usuários");

      const usuarios = await res.json();
      const tbody = document.querySelector("#tabelaUsuarios tbody");
      tbody.innerHTML = "";

      usuarios.forEach(u => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
          <td>${u.id}</td>
          <td>${u.nome}</td>
          <td>${u.email}</td>
          <td>${u.tipo}</td>
          <td>
            <button class="editar" data-id="${u.id}">✏️</button>
            <button class="excluir" data-id="${u.id}">🗑️</button>
          </td>
        `;
        tbody.appendChild(tr);
      });

      
      document.querySelectorAll(".editar").forEach(btn => {
        btn.addEventListener("click", async () => {
          const id = btn.dataset.id;
          try {
            const res = await fetch(`${API}/usuarios/${id}`);
            if (!res.ok) throw new Error("Erro ao buscar usuário");

            const u = await res.json();
            document.getElementById("id").value = u.id;
            document.getElementById("nome").value = u.nome;
            document.getElementById("email").value = u.email;
            document.getElementById("senha").value = ""; 
            document.getElementById("tipo").value = u.tipo;
          } catch (err) {
            console.error(err);
            mostrarMensagem("⚠ Erro ao carregar usuário.", "erro");
          }
        });
      });

      
      document.querySelectorAll(".excluir").forEach(btn => {
        btn.addEventListener("click", async () => {
          const id = btn.dataset.id;
          if (!confirm("Tem certeza que deseja excluir este usuário?")) return;

          try {
            const res = await fetch(`${API}/usuarios/${id}`, { method: "DELETE" });
            if (!res.ok) throw new Error("Erro ao excluir");

            mostrarMensagem("🗑️ Usuário excluído!");
            await carregarUsuarios();
          } catch (err) {
            console.error(err);
            mostrarMensagem("⚠ Erro ao excluir usuário.", "erro");
          }
        });
      });

    } catch (err) {
      console.error(err);
      mostrarMensagem("⚠ Erro ao carregar usuários.", "erro");
    }
  }


  carregarUsuarios();
}


  // Beneficiário
 
 if (tipo === "beneficiario") {
  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    
let dataBr = document.getElementById("dataNascimento")?.value || "";


let dataISO = "";
if (dataBr.includes("/")) {
  const [dia, mes, ano] = dataBr.split("/");
  dataISO = `${ano}-${mes}-${dia}`;
} else {
  dataISO = dataBr; 
}

const payload = {
  nome: document.getElementById("nome")?.value || "",
  dataNascimento: dataISO, 
  cpf: document.getElementById("cpf")?.value.replace(/\D/g, ""), 
  sexo: document.getElementById("sexo")?.value || "Outro",

  telefone: document.getElementById("telefone")?.value || "",
  email: document.getElementById("email")?.value || "",
  endereco: document.getElementById("endereco")?.value || "",
  bairro: document.getElementById("bairro")?.value || ""
};

console.log("Payload enviado:", JSON.stringify(payload, null, 2));


console.log("Payload enviado:", JSON.stringify(payload, null, 2));


    try {
      const res = await fetch(`${API}/beneficiarios`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });

      if (!res.ok) {
        mostrarMensagem("❌ Erro ao cadastrar beneficiário.", "erro");
        return;
      }

      mostrarMensagem("✅ Beneficiário cadastrado com sucesso!");
      form.reset();
    } catch (err) {
      console.error(err);
      mostrarMensagem("⚠ Falha de conexão com o servidor.", "erro");
    }
  });
}

  if (tipo === "agendamento") {
  form.addEventListener("submit", async (e) => {
    e.preventDefault();

 const payload = {
  usuario: { id: parseInt(document.getElementById("usuario").value) },
  beneficiario: { id: parseInt(document.getElementById("beneficiario").value) },
  dataAgendamento: document.getElementById("dataAgendamento").value, 
  horaAgendamento: document.getElementById("horaAgendamento").value + ":00", 
  tipoServico: document.getElementById("tipoServico").value,
  status: document.getElementById("status").value.toUpperCase(),
  localAtendimento: document.getElementById("localAtendimento").value,
  observacoes: document.getElementById("observacoes").value
};



    try {
      console.log("Payload enviado:", JSON.stringify(payload, null, 2));

      const res = await fetch(`${API}/agendamentos`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });

      if (!res.ok) throw new Error();

     mostrarMensagem(
  `✅ Agendamento confirmado para ${formatarData(payload.dataAgendamento)} às ${formatarHora(payload.horaAgendamento)}!`
);

      form.reset();
    } catch (err) {
      mostrarMensagem("⚠ Erro ao cadastrar agendamento", "erro");
    }
  });



async function carregarAgendamentos() {
  const res = await fetch(`${API}/agendamentos`);
  const data = await res.json();
  console.log("Resposta do backend:", data);

  
  if (!Array.isArray(data) && !Array.isArray(data.content)) {
    console.error("Backend não retornou lista!");
    return;
  }

  const lista = data.content || data;
  const tbody = document.querySelector("#tabelaAgendamentos tbody");
  tbody.innerHTML = "";

  lista.forEach(a => {
    
    let statusFormatado = a.status
      ? a.status.charAt(0).toUpperCase() + a.status.slice(1).toLowerCase()
      : "";

    const row = `<tr>
      <td>${a.id}</td>
      <td>${a.usuario?.id}</td>
      <td>${a.beneficiario?.id}</td>
      <td>${a.dataAgendamento}</td>
      <td>${a.horaAgendamento}</td>
      <td>${statusFormatado}</td>
    </tr>`;
    tbody.innerHTML += row;
  });
}




carregarAgendamentos();

  carregarAgendamentos();
}

});