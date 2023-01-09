import React, { useState, useEffect } from "react";
import { PlusOutlined, EditOutlined, DeleteOutlined } from "@ant-design/icons";
import { Button, Row, Modal, Input, message } from "antd";
import { Table } from "ant-table-extensions";
import { baseURL } from "../../../config";

const LanguagesCard = () => {
  const [data, setData] = useState();
  const [messageApi, resultMessageHolder] = message.useMessage();

  const [isEditing, setIsEditing] = useState(false);
  const [isAdding, setIsAdding] = useState(false);
  const [editingLanguage, setEditingLanguage] = useState(null);
  const [addingLanguage, setAddingLanguage] = useState(null);

  const [loading, setLoading] = useState(false);
  const [tableParams, setTableParams] = useState({
    pagination: {
      pageSize: 10,
    },
});

const success = (message) => {
  messageApi.open({
    type: "success",
    content: `${message}`,
    style: {
      marginTop: "150px",
    },
    duration: 2,
  });
};

const error = (message) => {
  messageApi.open({
    type: "error",
    content: `${message}`,
    style: {
      marginTop: "150px",
    },
    duration: 2,
  });
};

const columns = [
  {
    title: "Language name",
    dataIndex: "language",
    sorter: {
      compare: (a, b) => a.language.localeCompare(b.language),
    },
    editable: true,
  },
  {
    title: "Operations",
    render: (record) => {
      return (
        <>
          <EditOutlined
            onClick={() => {
              handleEdit(record);
            }}
          />
          <DeleteOutlined
            onClick={() => {
              handleDelete(record);
            }}
            style={{ color: "red", marginLeft: 12 }}
          />
        </>
      );
    },
  },
];

const fetchData = () => {
  setLoading(true);
  fetch(`${baseURL}/language`)
    .then((response) => response.json())
    .then((data) => {
      setData(data);
      setLoading(false);
    })
    .catch((error) => {
      console.error("Error while fetching data: ", error);
    });
};

useEffect(() => {
  fetchData();
  const interval = setInterval(() => {
    fetchData();
  }, 30000);
  return () => clearInterval(interval);
  // eslint-disable-next-line react-hooks/exhaustive-deps
}, [JSON.stringify(tableParams)]);
const handleTableChange = (pagination, filters, sorter) => {
  fetchData();
  setTableParams({
    pagination,
    filters,
    ...sorter,
  });
};

const deleteLanguage = (id) => {
  fetch(`${baseURL}/language/${id}`, {
    method: "DELETE",
  })
    .then((response) => {
      fetchData();
      if (response.ok) return response;
      else return response.json();
    })
    .then((res) => {
      if (res.ok) {
        success("Language deleted successfully!");
      } else {
        error(res.message);
      }
    })
    .catch((err) => {
      console.log("Error while deleting: ", err);
      error("Error! Couldn't delete language");
    });
};

const saveNewLanguage = () => {
  fetch(`${baseURL}/language`, {
    method: "POST",
    headers: {
      Accept: "application/json, text/plain",
      "Content-Type": "application/json;charset=UTF-8",
    },
    body: JSON.stringify(addingLanguage),
  })
    .then((response) => {
      fetchData();
      return response.json();
    })
    .then((res) => {
      if (res.language) {
        success("New language added successfully!");
        resetAdding();
      } else {
        error(res.message);
      }
    })
    .catch((err) => {
      console.log("Error while adding new language: ", err);
      error("Error! Couldn't add language");
    });
};


const saveEdited = () => {
  fetch(`${baseURL}/language`, {
    method: "POST",
    headers: {
      Accept: "application/json, text/plain",
      "Content-Type": "application/json;charset=UTF-8",
    },
    body: JSON.stringify(editingLanguage),
  })
    .then((response) => {
      fetchData();
      return response.json();
    })
    .then((res) => {
      if (res.language) {
        success("Language edited successfully!");
        resetEditing();
      } else {
        error(res.message);
      }
    })
    .catch((err) => {
      console.log("Error while edditing language: ", err);
      error("Error! Couldn't edit language");
    });
};

const onAdd = () => {
  setIsAdding(true);
  setAddingLanguage({ ...editingLanguage });
};

const handleDelete = (record) => {
  Modal.confirm({
    title: "Are you sure, you want to delete this language record?",
    okText: "Yes",
    okType: "danger",
    onOk: () => {
      deleteLanguage(record.id);
    },
  });
};

const resetEditing = () => {
  setIsEditing(false);
  setEditingLanguage(null);
};

const resetAdding = () => {
  setIsAdding(false);
  setAddingLanguage(null);
};

const handleEdit = (record) => {
  setIsEditing(true);
  setEditingLanguage({ ...record });
};

return (
  <>
    {resultMessageHolder}
    <Row justify="end">
      <Button
        onClick={onAdd}
        type="primary"
        icon={<PlusOutlined />}
        style={{ marginBottom: 16 }}
      >
        Add a new language
      </Button>
    </Row>
    <Table
      searchable
      searchableProps={{
        fuzzySearch: true,
        fuzzyProps: {
          ignoreLocation: true,
          includeScore: true,
          threshold: 0.0,
        },
      }}
      columns={columns}
      dataSource={data}
      rowKey={(record) => record.id}
      pagination={tableParams.pagination}
      loading={loading}
      size="middle"
      onChange={handleTableChange}
    />
    <Modal
      title="Edit language"
      open={isEditing}
      okText="Save"
      onCancel={() => {
        resetEditing();
      }}
      onOk={saveEdited}
    >
      <Input
        value={editingLanguage?.language}
        onChange={(e) => {
          setEditingLanguage((pre) => {
            return { ...pre, language: e.target.value };
          });
        }}
      ></Input>
    </Modal>

    <Modal
      title="Add new language"
      open={isAdding}
      okText="Save"
      onCancel={() => {
        resetAdding();
      }}
      onOk={saveNewLanguage}
    >
      <Input
        value={addingLanguage?.language}
        placeholder="language name"
        onChange={(e) => {
          setAddingLanguage((pre) => {
            return { ...pre, language: e.target.value };
          });
        }}
      ></Input>
    </Modal>
  </>
);
};

export default LanguagesCard;
