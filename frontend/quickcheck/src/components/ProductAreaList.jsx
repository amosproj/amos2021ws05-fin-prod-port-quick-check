import { React} from 'react';
import {
  List,
} from '@chakra-ui/react';


//components
import AddProjectAreaButton from "./AddProjectAreaButton.jsx"
import {AreaMock} from "./FetchAllAreas.jsx"
import RemoveProjectAreaButton from "./RemoveProjectAreaButton.jsx";
import ProductArea from "./ProductArea.jsx";

export default function ProductAreaList({ editMode, areaIDs, handleChange }) {
  const fetchArea = (areaID) => {
    return AreaMock[areaID];
  };

  const handleAddArea = (newID) => {
    handleChange([...areaIDs, newID]);
  };

  const handleRemoveArea = (removeID) => () => {
    const updatedAreaIDs = areaIDs.filter((m) => m !== removeID);
    handleChange(updatedAreaIDs);
  };

  return (
    <>
      <List w="50%" align="center" spacing={4} pb={5}>
        {areaIDs.map((id) => (
          <ProductArea
            productArea={fetchArea(id)}
            RemoveProjectAreaButton={editMode ? <RemoveProjectAreaButton onRemove={handleRemoveArea(id)} /> : <div />}
          />
        ))}
        {editMode ? <AddProjectAreaButton onAdd={handleAddArea}></AddProjectAreaButton> : <div />}
      </List>
    </>
  );
}
